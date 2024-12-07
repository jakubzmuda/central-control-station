import React from "react";
import styles from './secondaryButton.module.css';

function SecondaryButton({children, onClick = () => {}}) {
    return (
        <button className={styles.button} onClick={onClick}>
            {children}
        </button>
    );
}

export default SecondaryButton;
