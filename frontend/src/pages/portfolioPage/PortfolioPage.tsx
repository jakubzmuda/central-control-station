import React, {useCallback, useContext, useEffect} from "react";
import Page from "../../components/page/page";
import styles from "./portfolioPage.module.css"
import {useNavigate} from "react-router-dom";
import ConfirmationBar from "../../components/confirmationBar/confirmationBar";
import {AppContext} from "../../context/context";

function PortfolioPage() {

    const context = useContext(AppContext);
    const navigate = useNavigate();

    const fetchPortfolios = useCallback(async () => {
        try {
            await context.api.fetchPortfolios();
        } catch (e: any) {
            if (e.status === 401) {
                navigate('/no-access');
            }
        }
    }, [context.api, navigate]);

    useEffect(() => {
        fetchPortfolios();
    }, [fetchPortfolios]);

    return (
        <Page title={"Twoje akcyjki"} showUserSwitch={true}>
            <div className={styles.container}>
                <ConfirmationBar onSave={() => onSave()} onCancel={() => onCancel()}/>
            </div>
        </Page>
    );

    function onCancel() {
        navigate('/')
    }


    async function onSave() {
        await context.api.savePortfolio([]);
    }
}

export default PortfolioPage;
