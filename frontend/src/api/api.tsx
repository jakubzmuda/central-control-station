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
            const response = await this.axiosInstance.get('/api/portfolios', { headers: { 'Authorization': this.token() } });
            return response.data;
        } catch (error) {
            console.error('Error fetching user data:', error);
            throw error;
        }
    }

    private token() {
        const maybeToken = localStorage.getItem("token");
        return maybeToken ? "Bearer " + maybeToken : null;
    }

    private baseUrl() {
        return "http://kroker-control-station.eu-central-1.elasticbeanstalk.com/";
    }
}