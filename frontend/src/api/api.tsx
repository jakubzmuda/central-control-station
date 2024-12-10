import axios from "axios";
import AppStorage from "../storage/appStorage";
import {PortfolioEntry} from "../types/types";

export default class Api {

    constructor(private setUsers: Function, private setPortfolios: Function) {
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
            this.setPortfolios(response.data);
            this.setUsers([...Object.keys(response.data.portfolios)]);
        } catch (error) {
            console.error('Error fetching user data:', error);
            throw error;
        }
    }

    async savePortfolio(portfolioEntries: PortfolioEntry[]) {
        try {
            const request = {entries: portfolioEntries};
            const response = await this.axiosInstance.put(`/api/portfolios/${this.storage.currentUser()}`, request,{ headers: { 'Authorization': this.authHeader() } });
            return response.data;
        } catch (error) {
            console.error('Error fetching user data:', error);
            throw error;
        }
    }

    private authHeader() {
        const maybeToken = this.storage.token();
        return maybeToken ? "Bearer " + maybeToken : null;
    }

    private baseUrl() {
        return "http://kroker-control-station.eu-central-1.elasticbeanstalk.com/";
    }
}

