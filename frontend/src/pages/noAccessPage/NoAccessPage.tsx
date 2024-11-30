import React from "react";
import Page from "../../components/page/page";
import styles from "./noAccessPage.module.css"

function NoAccessPage() {
    return (
        <Page>
            <div className={styles.container}>
                <img src={`${process.env.PUBLIC_URL}/truwi_1.png`} alt={'angry truwi'} className={styles.image}/>
                <h3>Truwi spotted danger and started to is ready to inflict violence</h3>
                <h4>Unrecognized user</h4>
            </div>
        </Page>
    );
}

export default NoAccessPage;
