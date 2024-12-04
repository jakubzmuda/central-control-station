import React from "react";
import styles from './primaryButton.module.css';
import {MdMenu} from "react-icons/md";

function PrimaryButton({children, onClick = () => {}}) {
    return (
        <button className={styles.button} onClick={onClick}>
            {children}
        </button>
    );
}

export default PrimaryButton;
