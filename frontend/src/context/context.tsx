import {Context, createContext, useState} from 'react';
import Api from "../api/api";
import {Portfolio} from "../types/types";

type ContextType = Context<{
    api: Api,
    users: string[]
    portfolios:  Portfolio[]
}>;

// @ts-ignore
export const AppContext: ContextType = createContext(undefined);


export const AppContextProvider = ({children}) => {
    const [users, setUsers] = useState([]);
    const [portfolios, setPortfolios] = useState([]);
    const [api] = useState(new Api(setUsers, setPortfolios));

    return (
        <AppContext.Provider value={{api: api, users, portfolios}}>
            {children}
        </AppContext.Provider>
    );
};