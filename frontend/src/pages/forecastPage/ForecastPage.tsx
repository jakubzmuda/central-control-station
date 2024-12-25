import React, {useCallback, useContext, useEffect} from "react";
import Page from "../../components/page/page";
import styles from "./forecastPage.module.css"
import {useNavigate} from "react-router-dom";
import {AppContext} from "../../context/context";
import MonthlyBarChart from "../../components/monthlyBarChart/monthlyBarChart";
import {CurrencyConverter} from "../../currency/currencyConverter";
import {MoneyDisplay} from "../../moneyDisplay/MoneyDisplay";

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
        fetchCurrencyRates()
    }, [fetchCurrencyRates]);


    useEffect(() => {
        fetchForecast();
        // eslint-disable-next-line
    }, [fetchForecast, context.currentUser]);

    const fetchPortfolios = useCallback(async () => {
        try {
            await context.api.fetchPortfolios();
        } catch (e: any) {
            if (e.status === 401) {
                navigate('/no-access');
            }
        }
    }, [context.api, navigate]);

    useEffect(() => {
        fetchPortfolios();
    }, [fetchPortfolios]);

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

    function renderEarningSummary(label: string, amount: string) {
        return <div className={styles.earningsSummary}>
            <div className={styles.earningsLabel}>{label}</div>
            <div className={styles.earningsAmount}>{amount} zł</div>
        </div>
    }

    function renderChart() {
        if (context.forecast) {
            return <div className={styles.chartContainer}>
                <MonthlyBarChart forecast={context.forecast?.yearlyForecast}/>
            </div>
        }
    }

    function yearlyEarnings() {
        if (context.forecast && context.currencyRates["USD/PLN"]) {
            const amountInPln = new CurrencyConverter().inPln(context.forecast.yearlyForecast.total, context.currencyRates);
            const afterTax = amountInPln * 0.81;
            return new MoneyDisplay().asString(afterTax)
        }
        return "0";
    }

    function monthlyEarnings() {
        if (context.forecast && context.currencyRates) {
            const amountInPln = new CurrencyConverter().inPln(context.forecast.yearlyForecast.total, context.currencyRates);
            const afterTax = amountInPln * 0.81;
            return new MoneyDisplay().asString(afterTax / 12)
        }
        return "0";
    }


}

export default ForecastPage;
