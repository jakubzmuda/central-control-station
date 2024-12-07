import React, {useContext, useEffect} from "react";
import Page from "../../components/page/page";
import styles from "./portfolioPage.module.css"
import AppContext from "../../context";
import {useNavigate} from "react-router-dom";
import ConfirmationBar from "../../components/confirmationBar/confirmationBar";

function PortfolioPage() {

    const context = useContext(AppContext);
    const navigate = useNavigate();

    useEffect(() => {
       fetchPortfolio().catch(e => {
           if(e.status === 401) {
               navigate('/no-access');
           }
       })
    })


    return (
        <Page title={"Twoje akcyjki"}>
            <div className={styles.container}>
                <ConfirmationBar onSave={() => onSave()} onCancel={() => onCancel()}/>
            </div>
        </Page>
    );

    function onSave() {

    }

    function onCancel() {

    }

    async function fetchPortfolio() {
        await context.api.fetchPortfolios();
    }
}

export default PortfolioPage;
