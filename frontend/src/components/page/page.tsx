import React from "react";
import styles from './page.module.css';
import Header from "../header/header";
import UserSwitch from "../userSwitch/userSwitch";

function Page({title, children}: {title?: string, children: any}) {


    return (
        <div className={styles.page}>
            <Header />
            <div className={styles.header}>
                <h2 className={styles.title}>{title}</h2>
                <UserSwitch />
            </div>
            <div className={styles.pageContent}>
                {children}
            </div>
        </div>
    );
}

export default Page;
