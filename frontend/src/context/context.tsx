import {Context, createContext, useState} from 'react';
import Api from "../api/api";
import {Portfolio} from "../types/types";

type ContextType = Context<{
    api: Api,
    users: string[],
    portfolios:  Portfolio[],
    errorMessage: string
}>;

// @ts-ignore
export const AppContext: ContextType = createContext(undefined);


export const AppContextProvider = ({children}) => {
    const [users, setUsers] = useState([]);
    const [portfolios, setPortfolios] = useState([]);
    const [errorMessage, setErrorMessage] = useState('');
    const [api] = useState(new Api(setUsers, setPortfolios, setErrorMessage));

    return (
        <AppContext.Provider value={{api: api, users, portfolios, errorMessage}}>
            {children}
        </AppContext.Provider>
    );
};