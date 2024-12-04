import React from 'react';
import './App.css';
import NoAccessPage from "./pages/noAccessPage/NoAccessPage";
import {Navigate, Route, Routes} from "react-router-dom";
import NotFoundPage from "./pages/notFoundPage/NotFoundPage";
import PortfolioPage from "./pages/portfolioPage/PortfolioPage";
import LoginPage from "./pages/loginPage/LoginPage";

function App() {
    const defaultPage = '/portfolio';
    return (
    <div className="App">
        <Routes>
            <Route path="" element={<Navigate to={defaultPage} replace />} />
            <Route path="/" element={<Navigate to={defaultPage} replace />} />
            <Route path="/portfolio" element={<PortfolioPage />} />
            <Route path="/no-access" element={<NoAccessPage />} />
            <Route path="/not-found" element={<NotFoundPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="*" element={<Navigate to="/not-found" replace />} />
        </Routes>
    </div>
  );
}

export default App;
