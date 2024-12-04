import React, {useState} from "react";
import Page from "../../components/page/page";
import styles from "./loginPage.module.css"
import PrimaryButton from "../../components/primaryButton/primaryButton";
import {useNavigate} from "react-router-dom";


function LoginPage() {

    const [token, setToken] = useState("");
    const navigate = useNavigate();

    return (
        <Page title={"Login"}>
            <div className={styles.container}>
                <img src={`${process.env.PUBLIC_URL}/krokiet_3.png`} alt={'staring Krokiet'} className={styles.image}/>
                <h3>Krokiet watches you closely</h3>
                <textarea className={styles.token} placeholder={"token"} value={token} onChange={(e) => setToken(e.target.value)}></textarea>
                <PrimaryButton onClick={() => login()}>Yup that's me</PrimaryButton>
            </div>
        </Page>
    );

    function login() {
        localStorage.setItem('token', token);
        navigate('/portfolio');
    }
}


export default LoginPage;
