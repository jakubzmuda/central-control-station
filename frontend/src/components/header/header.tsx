import React from "react";
import styles from './header.module.css';
import MenuButton from "../menuButton/menuButton";
function Header() {
    return (
        <div className={styles.header}>
            <div className={styles.appName}>Krokiet i przyjaciele</div>
            <MenuButton />
        </div>
    );
}

export default Header;
