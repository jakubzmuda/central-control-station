export default class AppStorage {


    public token() {
        return localStorage.getItem("token")
    }

    private tokenKey = "token";
    private currentUserKey = "currentUser";
    private monthlySavingsKey = "monthlySavings";
    private monthlySpendingsKey = "monthlySpendings";

    public currentUser() {
        const maybeCurrentUser = localStorage.getItem(this.currentUserKey);
        return maybeCurrentUser || this.authenticatedUser();
    }

    public authenticatedUser() {
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

    public monthlySavings() {
        const maybeSavings = localStorage.getItem(this.monthlySavingsKey);
        return maybeSavings ? parseInt(maybeSavings) : 0;
    }

    public monthlySpendings() {
        const maybeSpendings = localStorage.getItem(this.monthlySpendingsKey);
        return maybeSpendings ? parseInt(maybeSpendings) : 5000;
    }

    public setCurrentUser(user: string) {
        localStorage.setItem(this.currentUserKey, user);
    }

    public setMonthlySpendings(spendings: number) {
        localStorage.setItem(this.monthlySpendingsKey, spendings.toString());
    }

    public setMonthlySavings(savings: number) {
        localStorage.setItem(this.monthlySavingsKey, savings.toString());
    }

    public setToken(token: string) {
        localStorage.setItem(this.tokenKey, token);
    }
}