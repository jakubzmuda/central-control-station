import React from "react";
import styles from './page.module.css';
import Header from "../header/header";

function Page({children}) {
    return (
        <div className={styles.page}>
            <Header />
            {children}
        </div>
    );
}

export default Page;
