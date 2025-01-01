import {Context, createContext, useState} from 'react';
import Api from "../api/api";
import {CurrencyRates, Portfolio, YearlyForecast} from "../types/types";
import AppStorage from "../storage/appStorage";


type ContextType = Context<{
    api: Api,
    users: string[],
    portfolios: { [key: string]: Portfolio },
    forecast?: Forecast,
    currencyRates: CurrencyRates,
    errorMessage: string,
    currentUser: string,
    setCurrentUser: Function,
    setMonthlySavings: Function,
    monthlySavings: number,
    monthlySpendings: number,
    setMonthlySpendings: Function,
}>;

type Forecast = { yearlyForecast: YearlyForecast } | null

// @ts-ignore
export const AppContext: ContextType = createContext(undefined);


export const AppContextProvider = ({children}) => {
    const appStorage = new AppStorage();

    const [users, setUsers] = useState([]);
    const [currentUser, setCurrentUser] = useState<string>(appStorage.currentUser());
    const [monthlySavings, setMonthlySavings] = useState<number>(appStorage.monthlySavings());
    const [monthlySpendings, setMonthlySpendings] = useState<number>(appStorage.monthlySpendings());
    const [portfolios, setPortfolios] = useState({});
    const [forecast, setForecast] = useState<Forecast>(null);
    const [errorMessage, setErrorMessage] = useState('');
    const [currencyRates, setCurrencyRates] = useState({});
    const [api] = useState(new Api(setUsers, setPortfolios, setErrorMessage, setForecast, setCurrencyRates));

    const setAndPersistCurrentUser = (user: string) => {
        appStorage.setCurrentUser(user)
        setCurrentUser(user);
    }

    const setAndPersistMonthlySavings = (savings: number) => {
        appStorage.setMonthlySavings(savings)
        setMonthlySavings(savings);
    }

    const setAndPersistMonthlySpendings = (spendings: number) => {
        appStorage.setMonthlySpendings(spendings)
        setMonthlySpendings(spendings);
    }

    return (
        <AppContext.Provider value={{
            api: api,
            users,
            portfolios,
            errorMessage,
            forecast,
            currencyRates,
            currentUser,
            setCurrentUser: setAndPersistCurrentUser,
            monthlySavings,
            setMonthlySavings: setAndPersistMonthlySavings,
            monthlySpendings,
            setMonthlySpendings: setAndPersistMonthlySpendings
        }}>
            {children}
        </AppContext.Provider>
    );
};