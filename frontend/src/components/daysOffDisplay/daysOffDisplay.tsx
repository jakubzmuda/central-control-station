import React from "react";
import styles from './daysOffDisplay.module.css';

function DaysOffDisplay({daysOff}: { daysOff: number }) {

    return (
        <div className={styles.container}>
            <h3>Dodatkowe dni wolne</h3>
            <div className={styles.daysOff}>{integerDaysOff()}</div>
            <div className={styles.progressContainer}>
                <div className={styles.daysOffSmall}>{integerDaysOff()}</div>
                <div className={styles.progressBar}>
                    <div className={styles.percentageLabel}>{(fractionalDaysOff() * 100).toFixed(0)} %</div>
                    <div className={styles.progress} style={{width: `calc(100% * ${fractionalDaysOff()})`}}></div>
                </div>
                <div className={styles.daysOffSmall}>{integerDaysOff() + 1}</div>
            </div>
            <div>Dzięki przychodom z inwestycji możesz wziąć {integerDaysOff()} dodatkowych dni wolnych w roku i nie
                odczuć tego finansowo ⛰️
            </div>
        </div>
    );

    function integerDaysOff() {
        return Math.floor(daysOff);
    }

    function fractionalDaysOff() {
        return daysOff - Math.trunc(daysOff);
    }


}

export default DaysOffDisplay;
