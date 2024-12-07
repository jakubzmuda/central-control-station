import axios from "axios";

export default class Api {

    private axiosInstance = axios.create({
        baseURL: this.baseUrl(),
        timeout: 10000,
        headers: {
            'Content-Type': 'application/json',
        },
    });

    async fetchPortfolios() {
        try {
            const response = await this.axiosInstance.get('/api/portfolios', { headers: { 'Authorization': this.authHeader() } });
            return response.data;
        } catch (error) {
            console.error('Error fetching user data:', error);
            throw error;
        }
    }

    async savePortfolio(portfolioEntries: PortfolioEntry[]) {
        try {
            const currentUser = this.currentUser()
            const request = {entries: portfolioEntries};
            const response = await this.axiosInstance.put(`/api/portfolios/${currentUser}`, request,{ headers: { 'Authorization': this.authHeader() } });
            return response.data;
        } catch (error) {
            console.error('Error fetching user data:', error);
            throw error;
        }
    }

    private authHeader() {
        const maybeToken = localStorage.getItem("token");
        return maybeToken ? "Bearer " + maybeToken : null;
    }

    private token() {
        return localStorage.getItem("token")
    }

    private authenticatedUser() {
        try {
            const token = this.token();
            if(!token) {
                return null;
            }
            const payload = token.split('.')[1];
            const decodedPayload = JSON.parse(atob(payload.replace(/-/g, '+').replace(/_/g, '/')));
            return decodedPayload['user'];
        } catch (error) {
            console.error('Error decoding JWT:', error);
            return null;
        }
    }

    private currentUser() {
        const maybeCurrentUser = localStorage.getItem("currentUser");
        return maybeCurrentUser || this.authenticatedUser();
    }

    private baseUrl() {
        return "http://kroker-control-station.eu-central-1.elasticbeanstalk.com/";
    }
}

type PortfolioEntry = {

}