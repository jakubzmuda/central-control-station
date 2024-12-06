import React from "react";
import styles from './primaryButton.module.css';

function PrimaryButton({children, onClick = () => {}}) {
    return (
        <button className={styles.button} onClick={onClick}>
            {children}
        </button>
    );
}

export default PrimaryButton;
