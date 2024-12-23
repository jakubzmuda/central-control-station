import axios from "axios";
import AppStorage from "../storage/appStorage";
import {PortfolioEntry} from "../types/types";

export default class Api {

    constructor(private setUsers: Function, private setPortfolios: Function, private setErrorMessage: Function, private setForecast: Function, private setCurrencyRates: Function) {
    }

    private axiosInstance = axios.create({
        baseURL: this.baseUrl(),
        timeout: 10000,
        headers: {
            'Content-Type': 'application/json',
        },
    });

    private storage = new AppStorage();

    async fetchPortfolios() {
        try {
            const response = await this.axiosInstance.get('/api/portfolios', { headers: { 'Authorization': this.authHeader() } });
            this.setPortfolios(response.data.portfolios);
            this.setUsers([...Object.keys(response.data.portfolios)]);
            return response.data.portfolios
        } catch (error: any) {
            this.setErrorMessage(`Mamy problemik z załadowaniem portfolio \n ${error.message}`)
            throw error;
        }
    }

    async savePortfolio(portfolioEntries: PortfolioEntry[]) {
        try {
            const request = {entries: portfolioEntries};
            const response = await this.axiosInstance.put(`/api/portfolios/${this.storage.currentUser()}`, request,{ headers: { 'Authorization': this.authHeader() } });
            return response.data;
        } catch (error: any) {
            this.setErrorMessage(`Nie dało rady zapisać portfolio \n ${error.message}`)
            throw error;
        }
    }

    async fetchForecast() {
        try {
            const response = await this.axiosInstance.get(`/api/distributions/forecast?user=${this.storage.currentUser()}`,{ headers: { 'Authorization': this.authHeader() } });
            this.setForecast(response.data);
            return response.data;
        } catch (error: any) {
            this.setErrorMessage(`Wołaj starego, nie dało rady podliczyć przychodów \n ${error.message}`)
            throw error;
        }
    }

    async fetchCurrencyRates() {
        try {
            const response = await this.axiosInstance.get(`/api/currency-rates`,{ headers: { 'Authorization': this.authHeader() } });
            const rates = response.data.rates;
            this.setCurrencyRates(rates);
            return rates;
        } catch (error: any) {
            this.setErrorMessage(`Nie udało się pobrać kursów walut. \n ${error.message}`)
            throw error;
        }
    }

    private authHeader() {
        const maybeToken = this.storage.token();
        return maybeToken ? "Bearer " + maybeToken : null;
    }

    private baseUrl() {
        return "https://api.krokiecik.shop";
    }
}

