import React from "react";
import styles from './header.module.css';
import MenuButton from "../menuButton/menuButton";
import AppIcon from "../appIcon/appIcon";

function Header() {
    return (
        <div className={styles.header}>
            <div className={styles.appName}>Krokiet</div>
            <MenuButton />
        </div>
    );
}

export default Header;
