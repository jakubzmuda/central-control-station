import React from 'react';
import './App.css';
import NoAccessPage from "./pages/noAccessPage/NoAccessPage";
import {Navigate, Route, Routes} from "react-router-dom";
import NotFoundPage from "./pages/notFoundPage/NotFoundPage";
import PortfolioPage from "./pages/portfolioPage/PortfolioPage";
import LoginPage from "./pages/loginPage/LoginPage";
import {AppContextProvider} from "./context/context";
import ForecastPage from "./pages/forecastPage/ForecastPage";

function App() {
    const defaultPage = '/forecast';
    return (
        <AppContextProvider>
            <div className="App">
                <Routes>
                    <Route path="" element={<Navigate to={defaultPage} replace/>}/>
                    <Route path="/" element={<Navigate to={defaultPage} replace/>}/>
                    <Route path="/portfolio" element={<PortfolioPage/>}/>
                    <Route path="/forecast" element={<ForecastPage/>}/>
                    <Route path="/no-access" element={<NoAccessPage/>}/>
                    <Route path="/not-found" element={<NotFoundPage/>}/>
                    <Route path="/login" element={<LoginPage/>}/>
                    <Route path="*" element={<Navigate to="/not-found" replace/>}/>
                </Routes>
            </div>
        </AppContextProvider>
    );
}

export default App;
