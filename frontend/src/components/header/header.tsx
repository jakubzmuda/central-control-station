import React from "react";
import styles from './header.module.css';
import MenuButton from "../menuButton/menuButton";
import {useNavigate} from "react-router-dom";
function Header() {

    const navigate = useNavigate();

    return (
        <div className={styles.header}>
            <div className={styles.appName} onClick={() => navigate('/')}>Krokiet i przyjaciele</div>
            <MenuButton />
        </div>
    );
}

export default Header;
