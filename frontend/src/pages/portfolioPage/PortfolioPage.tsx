import React, {useCallback, useContext, useEffect, useState} from "react";
import Page from "../../components/page/page";
import styles from "./portfolioPage.module.css"
import {useNavigate} from "react-router-dom";
import ConfirmationBar from "../../components/confirmationBar/confirmationBar";
import {AppContext} from "../../context/context";
import {PortfolioEntry} from "../../types/types";
import PrimaryButton from "../../components/primaryButton/primaryButton";
import {MdDelete} from "react-icons/md";

function PortfolioPage() {

    const context = useContext(AppContext);
    const navigate = useNavigate();
    const [portfolioEntries, setPortfolioEntries] = useState<EnhancedPortfolioEntry[]>([])

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

    function renderEntries() {
        return <>
            {portfolioEntries.map(entry =>
                <div key={entry.key} className={styles.portfolioLine}>
                    <input className={styles.productTicker} value={entry.productTicker} onChange={(e) => updateTicker(entry.key, entry.productTicker, e.target.value)}/>
                    <input className={styles.amount} value={entry.amount} />
                    <div className={styles.binContainer} onClick={() => deleteEntry(entry.productTicker)}><MdDelete color={"#E80F88"} size={32}/></div>
                </div>
            )}
        </>;
    }

    return (
        <Page title={"Twoje akcyjki"} showUserSwitch={true}>
            <div className={styles.container}>
                {renderEntries()}
                <PrimaryButton onClick={() => addEntry()}>Nowa pozycja</PrimaryButton>
                <ConfirmationBar onSave={() => onSave()} onCancel={() => onCancel()}/>
            </div>
        </Page>
    );

    function addEntry() {
        setPortfolioEntries((prevState) => [...prevState, {productTicker: '', amount: 0, key: Math.random().toString(36).substr(2, 9)}])
    }

    function deleteEntry(ticker: string) {
        setPortfolioEntries((prevState) => [...prevState].filter(entry => entry.productTicker !== ticker))
    }

    function updateTicker(key: string, oldTicker: string, newTicker: string) {
        setPortfolioEntries((prevState) => {
            const updatedEntry = {...prevState.find(e => e.key === key)!, productTicker: newTicker}
            return [...prevState, updatedEntry].filter(e => e.productTicker !== oldTicker);
        })
    }

    function onCancel() {
        navigate('/')
    }

    async function onSave() {
        await context.api.savePortfolio(portfolioEntries);
    }
}

interface EnhancedPortfolioEntry extends PortfolioEntry {
    key: string
}

export default PortfolioPage;
