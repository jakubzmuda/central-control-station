import React, {useContext, useEffect} from "react";
import Page from "../../components/page/page";
import styles from "./portfolioPage.module.css"
import PrimaryButton from "../../components/primaryButton/primaryButton";
import AppContext from "../../context";
import {useNavigate} from "react-router-dom";

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
                <PrimaryButton>Add</PrimaryButton>
            </div>
        </Page>
    );


    async function fetchPortfolio() {
        await context.api.fetchPortfolios();
    }
}

export default PortfolioPage;
