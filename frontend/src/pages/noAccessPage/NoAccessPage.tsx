import React from "react";
import Page from "../../components/page/page";
import styles from "./noAccessPage.module.css"
import PrimaryButton from "../../components/primaryButton/primaryButton";
import {useNavigate} from "react-router-dom";

function NoAccessPage() {

    const navigate = useNavigate();


    return (
        <Page>
            <div className={styles.container}>
                <img src={`${process.env.PUBLIC_URL}/truwi_1.png`} alt={'angry truwi'} className={styles.image}/>
                <h3>Truwi spotted danger and is ready to inflict violence</h3>
                <h4>Unrecognized user</h4>
                <PrimaryButton onClick={() => navigate("/login")}>Log in</PrimaryButton>
            </div>
        </Page>
    );
}

export default NoAccessPage;
