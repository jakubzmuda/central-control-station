import React, {useContext} from "react";
import Page from "../../components/page/page";
import {AppContext} from "../../context/context";
import styles from './goalsPage.module.css';

function GoalsPage() {

    const context = useContext(AppContext);

    return (
        <Page title={"Cele"} showUserSwitch={true}>
            <div className={styles.container}>
                <div>Zakładając, że wydajesz miesięcznie:</div>
                <input/>
            </div>

        </Page>
    );

}

export default GoalsPage;
