import React, {useContext, useEffect, useState} from "react";
import Page from "../../components/page/page";
import styles from "./portfolioPage.module.css"
import PrimaryButton from "../../components/primaryButton/primaryButton";
import AppContext from "../../context";

function PortfolioPage() {

    const context = useContext(AppContext);

    useEffect(() => {
       fetchPortfolio()
    }, [])

    return (
        <Page title={"Twoje akcyjki"}>
            <div className={styles.container}>
                <PrimaryButton>Add</PrimaryButton>
            </div>
        </Page>
    );


    function fetchPortfolio() {
        context.api.fetchPortfolios();
    }
}

export default PortfolioPage;
