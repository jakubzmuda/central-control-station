import React, {useCallback, useContext, useEffect} from "react";
import Page from "../../components/page/page";
import styles from "./forecastPage.module.css"
import {useNavigate} from "react-router-dom";
import {AppContext} from "../../context/context";
import MonthlyBarChart from "../../components/monthlyBarChart/monthlyBarChart";

function ForecastPage() {

    const context = useContext(AppContext);
    const navigate = useNavigate();

    const fetchForecast = useCallback(async () => {
        try {
            await context.api.fetchForecast();
        } catch (e: any) {
            if (e.status === 401) {
                navigate('/no-access');
            }
        }
    }, [context.api, navigate]);

    const fetchCurrencyRates = useCallback(async () => {
        try {
            await context.api.fetchCurrencyRates();
        } catch (e: any) {
            if (e.status === 401) {
                navigate('/no-access');
            }
        }
    }, [context.api, navigate]);

    useEffect(() => {
        fetchForecast();
        fetchCurrencyRates()
    }, [fetchForecast, fetchCurrencyRates]);

    return (
        <Page title={"Twoje przychody"} showUserSwitch={true}>
            <div className={styles.container}>
                {renderForecast()}
                {renderChart()}
            </div>
        </Page>
    );

    function renderForecast() {
        if (context.forecast) {
            return <div className={styles.earningsSummaryContainer}>
                {renderEarningSummary('Rocznie', yearlyEarnings())}
                {renderEarningSummary('Miesięcznie', monthlyEarnings())}
            </div>
        }
        return "ładuję"
    }

    function renderEarningSummary(label: string, amount: number = 0) {
        return <div className={styles.earningsSummary}>
            <div className={styles.earningsLabel}>{label}</div>
            <div className={styles.earningsAmount}>{parseFloat(amount.toFixed(2))} zł</div>
        </div>
    }

    function renderChart() {
        return <div className={styles.chartContainer}>
            <MonthlyBarChart />
        </div>
    }

    function yearlyEarnings() {
        const usdPlnRate = context.currencyRates['USD/PLN'];
        if (context.forecast && usdPlnRate) {
            return context.forecast.yearlyForecast.total["USD"] * usdPlnRate;
        }
        return 0;
    }

    function monthlyEarnings() {
        return yearlyEarnings() / 12;
    }


}

export default ForecastPage;
