import React, {useCallback, useContext, useEffect} from "react";
import Page from "../../components/page/page";
import {AppContext} from "../../context/context";
import styles from './futurePage.module.css';
import Switch from "../../components/switch/switch";
import FutureGainsBarChart from "../../components/futureGainsBarChart/futureGainsBarChart";
import {useNavigate} from "react-router-dom";

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
                    <Switch entries={generateIncrements(0, 5000, 100)} value={context.monthlySavings} onChange={(value: number) => context.setMonthlySavings(value)}/>
                    <div>zł.</div>
                </div>
                <div className={styles.inputContainer}>
                    <div>Moje miesięczne wydatki to</div>
                    <Switch entries={generateIncrements(0, 10000, 500)} value={context.monthlySpendings} onChange={(value: number) => context.setMonthlySpendings(value)}/>
                    <div>zł.</div>
                </div>
                <div className={styles.chartContainer}>
                    <h3>Prognoza miesięcznych przychodów</h3>
                    <FutureGainsBarChart />
                </div>
            </div>

        </Page>
    );

    function generateIncrements(start: number, end: number, step: number): number[] {
        if (start > end) {
            throw new Error("Start value must be less than or equal to end value.");
        }

        const increments: number[] = [];
        for (let i = start; i <= end; i += step) {
            increments.push(i);
        }
        return increments;
    }

}

export default FuturePage;
