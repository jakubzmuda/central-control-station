import React, {useContext} from "react";
import Page from "../../components/page/page";
import styles from "./forecastPage.module.css"
import {useNavigate} from "react-router-dom";
import {AppContext} from "../../context/context";
import AppStorage from "../../storage/appStorage";

function ForecastPage() {

    const appStorage = new AppStorage();
    const context = useContext(AppContext);
    const navigate = useNavigate();


    return (
        <Page title={"Twoje przychody"} showUserSwitch={true}>
            <div className={styles.container}>
                Hej
            </div>
        </Page>
    );


}

export default ForecastPage;
