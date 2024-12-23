export default class AppStorage {


    public token() {
        return localStorage.getItem("token")
    }

    private tokenKey = "token";
    private currentUserKey = "currentUser";

    public currentUser() {
        console.log('asked', localStorage.getItem(this.currentUserKey))
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

    public setCurrentUser(user: string) {
        console.log('set', user)
        localStorage.setItem(this.currentUserKey, user);
    }

    public setToken(token: string) {
        localStorage.setItem(this.tokenKey, token);
    }
}