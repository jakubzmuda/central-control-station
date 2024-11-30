import React from "react";
import styles from './page.module.css';
import Header from "../header/header";

function Page({title, children}: {title?: string, children: any}) {
    console.log("title", title)
    return (
        <div className={styles.page}>
            <Header />
            <h2 className={styles.title}>{title}</h2>
            <div className={styles.pageContent}>
                {children}
            </div>
        </div>
    );
}

export default Page;
