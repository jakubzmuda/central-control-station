import React from "react";
import Page from "../../components/page/page";
import styles from "./portfolioPage.module.css"

function PortfolioPage() {
    return (
        <Page>
            <div className={styles.container}>
                Portfolio
            </div>
        </Page>
    );
}

export default PortfolioPage;
