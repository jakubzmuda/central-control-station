import React from "react";
import styles from './menuButton.module.css';
import {MdMenu} from "react-icons/md";

function MenuButton() {
    return (
        <button className={styles.button}>
            <MdMenu color={"#E80F88"} size={40}/>
        </button>
    );
}

export default MenuButton;
