import {Context, createContext} from 'react';
import Api from "./api/api";

const AppContext: Context<{api: Api}> = createContext({api: new Api()});
export default AppContext;