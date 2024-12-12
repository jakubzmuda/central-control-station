import React from "react";
import styles from './page.module.css';
import Header from "../header/header";
import UserSwitch from "../userSwitch/userSwitch";
import ConfirmationBar from "../confirmationBar/confirmationBar";
import ErrorBubble from "../errorBubble/errorBubble";

function Page({title, showUserSwitch, onSave, onCancel, children}: {title?: string, children: any, showUserSwitch?: boolean, onSave?: Function, onCancel?: Function}) {

    return (
        <div className={styles.page}>
            <Header />
            <div className={styles.header}>
                <h2 className={styles.title}>{title}</h2>
                {showUserSwitch ? <UserSwitch /> : null}
            </div>
            <div className={styles.pageContent}>
                {children}
            </div>
            <ErrorBubble />
            {onSave && onCancel ? <ConfirmationBar onSave={() => onSave()} onCancel={() => onCancel()}/> : null}
        </div>
    );
}

export default Page;
