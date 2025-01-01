import React, {useContext} from "react";
import Page from "../../components/page/page";
import {AppContext} from "../../context/context";
import styles from './futurePage.module.css';
import Switch from "../../components/switch/switch";

function FuturePage() {

    const context = useContext(AppContext);

    return (
        <Page title={"Przyszłość"} showUserSwitch={true}>
            <div className={styles.container}>
                <div className={styles.inputContainer}>
                    <div>Chcę oszczędzać miesięcznie</div>
                    <Switch entries={generateIncrements(0, 5000, 100)} value={context.monthlySavings} onChange={(value) => context.setMonthlySavings(value)}/>
                    <div>zł.</div>
                </div>
                <div className={styles.inputContainer}>
                    <div>Moje miesięczne wydatki to</div>
                    <Switch entries={generateIncrements(0, 10000, 500)} value={context.monthlySpendings} onChange={(value) => context.setMonthlySpendings(value)}/>
                    <div>zł.</div>
                </div>
            </div>

        </Page>
    );

    function generateIncrements(start: number, end: number, step: number): number[] {
        if (start > end) {
            throw new Error("Start value must be less than or equal to end value.");
        }

        const increments: number[] = [];
        for (let i = start; i <= end; i += step) {
            increments.push(i);
        }
        return increments;
    }

}

export default FuturePage;
