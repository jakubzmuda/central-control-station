import React, {useCallback, useContext, useEffect} from "react";
import Page from "../../components/page/page";
import {AppContext} from "../../context/context";
import styles from './futurePage.module.css';
import Switch from "../../components/switch/switch";
import FutureGainsBarChart from "../../components/futureGainsBarChart/futureGainsBarChart";
import {useNavigate} from "react-router-dom";
import DaysOffDisplay from "../../components/daysOffDisplay/daysOffDisplay";
import {CurrencyConverter} from "../../currency/currencyConverter";

function FuturePage() {

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
        <Page title={"Przyszłość"} showUserSwitch={true}>
            <div className={styles.container}>
                <div className={styles.inputContainer}>
                    <div>Chcę oszczędzać miesięcznie</div>
                    <Switch entries={generateIncrements(0, 15000)} value={context.monthlySavings}
                            onChange={(value: number) => context.setMonthlySavings(value)}/>
                    <div>zł.</div>
                </div>
                <div className={styles.inputContainer}>
                    <div>Moje miesięczne wydatki to</div>
                    <Switch entries={generateIncrements(0, 15000)} value={context.monthlySpendings}
                            onChange={(value: number) => context.setMonthlySpendings(value)}/>
                    <div>zł.</div>
                </div>
                <div className={styles.daysOffContainer}>
                    <DaysOffDisplay daysOff={daysOff()}/>
                </div>
                <div className={styles.chartContainer}>
                    <h3>Prognoza miesięcznych przychodów</h3>
                    <FutureGainsBarChart/>
                </div>
            </div>
        </Page>
    );

    function daysOff(): number {
        const yearlySpendings = context.monthlySpendings * 12;
        const spendingsCoverageFactor = yearlyEarnings() / yearlySpendings;
        return spendingsCoverageFactor * 365.25;
    }

    function yearlyEarnings() {
        if (context.forecast && context.currencyRates) {
            const amountInPln = new CurrencyConverter().inPln(context.forecast.yearlyForecast.total, context.currencyRates);
            return amountInPln * 0.81;
        }
        return 0;
    }

    function generateIncrements(start: number, end: number): number[] {
        if (start > end) {
            throw new Error("Start value must be less than or equal to end value.");
        }

        const increments: number[] = [];
        for (let i = start; i <= end; i += step(i)) {
            increments.push(i);
        }
        return increments;
    }

    function step(amount: number): number {
        if (amount < 1000) {
            return 100;
        }
        if (amount < 5000) {
            return 500;
        }
        return 1000;
    }

}

export default FuturePage;
