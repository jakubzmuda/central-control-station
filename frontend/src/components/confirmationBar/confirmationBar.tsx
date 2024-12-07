import React from "react";
import styles from './confirmationBar.module.css';
import PrimaryButton from "../primaryButton/primaryButton";
import SecondaryButton from "../secondaryButton/secondaryButton";

function ConfirmationBar({onSave, onCancel}) {
    return (
        <div className={styles.container}>
            <SecondaryButton onClick={onCancel}>może potem</SecondaryButton>
            <PrimaryButton onClick={onSave}>zapisujemy</PrimaryButton>
        </div>
    );
}

export default ConfirmationBar;
