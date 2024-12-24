import React from "react";
import styles from './navigationBar.module.css';
import {MdAttachMoney, MdInsertEmoticon, MdLogin, MdWorkOutline} from "react-icons/md";
import {useLocation, useNavigate} from "react-router-dom";

function NavigationBar() {

    const navigate = useNavigate();
    const location = useLocation();

    return (
        <div className={styles.container}>
            {renderNavigationItem('/portfolio', <MdWorkOutline size={24} color={"#E80F88"}/>)}
            {renderNavigationItem('/forecast', <MdAttachMoney size={24} color={"#E80F88"}/>)}
            {renderNavigationItem('/goals', <MdInsertEmoticon  size={24} color={"#E80F88"}/>)}
            {renderNavigationItem('/login', <MdLogin size={24} color={"#E80F88"}/>)}
        </div>
    );

    function renderNavigationItem(path: string, component: any) {
        return <div
            className={styles.navigationItem}
            onClick={() => navigate(path)}>
            {React.cloneElement(component, {
                color: location.pathname === path ? '#E80F88' : '#fff',
            })}
        </div>
    }
}

export default NavigationBar;
