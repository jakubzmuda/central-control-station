import React from "react";
import Page from "../../components/page/page";
import styles from "./notFoundPage.module.css"

function NotFoundPage() {
    return (
        <Page>
            <div className={styles.container}>
                <img src={`${process.env.PUBLIC_URL}/krokiet_1.png`} alt={'confused Krokiet'} className={styles.image}/>
                <h3>Krokiet is as confused as you are</h3>
                <h4>Page not found</h4>
            </div>
        </Page>
    );
}

export default NotFoundPage;
