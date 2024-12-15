import React, {useCallback, useContext, useEffect, useState} from "react";
import Page from "../../components/page/page";
import styles from "./portfolioPage.module.css"
import {useNavigate} from "react-router-dom";
import {AppContext} from "../../context/context";
import PrimaryButton from "../../components/primaryButton/primaryButton";
import {MdDelete} from "react-icons/md";
import AppStorage from "../../storage/appStorage";

/* eslint-disable react-hooks/exhaustive-deps */
function PortfolioPage() {

    const appStorage = new AppStorage();
    const context = useContext(AppContext);
    const navigate = useNavigate();
    const [portfolioEntries, setPortfolioEntries] = useState<ReactPortfolioEntry[]>([])

    const fetchPortfolios = useCallback(async () => {
        try {
            const portfolios = await context.api.fetchPortfolios();
            setPortfolioEntries(portfolios[appStorage.currentUser()].entries.map(entry => ({
                key: randomKey(),
                productTicker: entry.productTicker,
                amount: String(entry.amount)
            })));
        } catch (e: any) {
            if (e.status === 401) {
                navigate('./no-access');
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
                    <div className={styles.productTickerContainer}>
                        <input className={styles.productTicker} value={entry.productTicker}
                               onChange={(e) => updateTicker(entry.key, e.target.value)}/>
                    </div>
                    <div className={styles.amountContainer}>
                        <input className={styles.amount} value={entry.amount} onChange={e => updateAmount(entry.key, e.target.value)}/>
                    </div>
                    <div className={styles.binContainer} onClick={() => deleteEntry(entry.key)}><MdDelete color={"#E80F88"} size={32}/></div>
                </div>
            )}
        </>;
    }

    return (
        <Page title={"Twoje akcyjki"} showUserSwitch={true} onSave={onSave} onCancel={onCancel}>
            <div className={styles.container}>
                {renderEntries()}
                <PrimaryButton onClick={() => addEntry()}>Nowa pozycja</PrimaryButton>
            </div>
        </Page>
    );

    function addEntry() {
        setPortfolioEntries((prevState) => [...prevState, {productTicker: '', amount: "0", key: randomKey()}])
    }

    function deleteEntry(key: string) {
        setPortfolioEntries((prevState) => [...prevState].filter(entry => entry.key !== key))
    }

    function updateTicker(key: string, newTicker: string) {
        setPortfolioEntries((prevState) => {
            return prevState.map((entry) => {
                if (entry.key === key) {
                    return {...entry, productTicker: newTicker};
                }
                return entry;
            });
        });
    }

    function updateAmount(key: string, newAmount: string) {
        setPortfolioEntries((prevState) => {
            return prevState.map((entry) => {
                if (entry.key === key) {
                    return {...entry, amount: newAmount};
                }
                return entry;
            });
        });
    }

    function onCancel() {
        navigate('./')
    }

    async function onSave() {
        await context.api.savePortfolio(portfolioEntries.map(e => ({productTicker: e.productTicker, amount: parseFloat(e.amount)})));
        navigate('./forecast')
    }

    function randomKey() {
        return Math.random().toString(36).substr(2, 9);
    }
}

interface ReactPortfolioEntry {
    key: string,
    productTicker: string,
    amount: string
}

export default PortfolioPage;
