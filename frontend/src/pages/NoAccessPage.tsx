import React from "react";
import Page from "../components/page/page";
import styles from "./noAccessPage.module.css"

function NoAccessPage() {
    return (
        <Page>
            <div className={styles.container}>
                <img src={'/truwi_1.png'} alt={'angry truwi'} className={styles.image}/>
                <div>Truwi will not let you in, stranger</div>
            </div>
        </Page>
    );
}

export default NoAccessPage;
