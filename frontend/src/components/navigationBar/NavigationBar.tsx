import React from "react";
import styles from './navigationBar.module.css';
import MenuButton from "../menuButton/menuButton";
import {useNavigate} from "react-router-dom";
import {MdAttachMoney, MdLogin, MdMood, MdWorkOutline} from "react-icons/md";

function NavigationBar() {

    const navigate = useNavigate();

    return (
        <div className={styles.container}>
            <div className={styles.navigationItem} onClick={() => navigate('/portfolio')}>
                <MdWorkOutline size={24} color={"#E80F88"}/>
            </div>
            <div className={styles.navigationItem} onClick={() => navigate('/forecast')}>
                <MdAttachMoney size={24} color={"#E80F88"}/>
            </div>
            <div className={styles.navigationItem} onClick={() => navigate('/login')}>
                <MdLogin size={24} color={"#E80F88"}/>
            </div>
        </div>
    );

}

export default NavigationBar;
