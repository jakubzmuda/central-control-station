import {Context, createContext, useState} from 'react';
import Api from "../api/api";
import {CurrencyRates, Portfolio, YearlyForecast} from "../types/types";


type ContextType = Context<{
    api: Api,
    users: string[],
    portfolios: { [key: string]: Portfolio },
    forecast?: Forecast,
    currencyRates: CurrencyRates,
    errorMessage: string
}>;

type Forecast = { yearlyForecast: YearlyForecast } | null

// @ts-ignore
export const AppContext: ContextType = createContext(undefined);


export const AppContextProvider = ({children}) => {
    const [users, setUsers] = useState([]);
    const [portfolios, setPortfolios] = useState({});
    const [forecast, setForecast] = useState<Forecast>(null);
    const [errorMessage, setErrorMessage] = useState('');
    const [currencyRates, setCurrencyRates] = useState({});
    const [api] = useState(new Api(setUsers, setPortfolios, setErrorMessage, setForecast, setCurrencyRates));

    return (
        <AppContext.Provider value={{api: api, users, portfolios, errorMessage, forecast, currencyRates}}>
            {children}
        </AppContext.Provider>
    );
};