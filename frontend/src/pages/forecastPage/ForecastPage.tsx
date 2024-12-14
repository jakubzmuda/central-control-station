import React, {useCallback, useContext, useEffect} from "react";
import Page from "../../components/page/page";
import styles from "./forecastPage.module.css"
import {useNavigate} from "react-router-dom";
import {AppContext} from "../../context/context";
import AppStorage from "../../storage/appStorage";

function ForecastPage() {

    const appStorage = new AppStorage();
    const context = useContext(AppContext);
    const navigate = useNavigate();

    const fetchForecast = useCallback(async () => {
        try {
            const portfolios = await context.api.fetchForecast();
        } catch (e: any) {
            if (e.status === 401) {
                navigate('/no-access');
            }
        }
    }, [context.api, navigate]);

    useEffect(() => {
        fetchForecast();
    }, [fetchForecast]);

    return (
        <Page title={"Twoje przychody"} showUserSwitch={true}>
            <div className={styles.container}>
                {renderForecast()}
            </div>
        </Page>
    );


    function renderForecast() {
        if (context.forecast) {
            console.log(context.forecast)
            return <div className={styles.forecastContainer}>
                <div>Twój roczny przychód</div>
                <div>{context.forecast.yearlyForecast.total["USD"]} USD</div>
            </div>
        }
        return "ładuję"
    }


}

export default ForecastPage;