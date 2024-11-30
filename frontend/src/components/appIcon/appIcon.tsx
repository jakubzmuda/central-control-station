import React from "react";
import styles from './appIcon.module.css';

function AppIcon() {
    return (
        <img src={'/krokiet_icon.png'} alt={'Krokiet Control Station'} className={styles.image}/>
    );
}

export default AppIcon;
