import React, {useContext} from "react";
import {BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip,} from 'chart.js';
import {AppContext} from "../../context/context";
import BarChart from "../barChart/barChart";
import {CurrencyConverter} from "../../currency/currencyConverter";


ChartJS.register(CategoryScale, LinearScale, BarElement, Legend, Title, Tooltip);

function FutureGainsBarChart() {

    const context = useContext(AppContext);

    abstract class Component {
        abstract name: string;
        abstract borderColor: string;
        abstract backgroundColor: string;

        abstract valueInYear(year: number): number;
    }

    class BaseComponent extends Component {
        name = 'obecnie';
        borderColor = '#fff';
        backgroundColor = 'rgba(255,255,255,0.4)';

        valueInYear(year: number) {
            return currentMonthlyEarnings()
        }
    }

    class NewSavingsComponent extends Component {
        name = 'nowe oszczędności';
        borderColor = '#327fcc';
        backgroundColor = 'rgba(50,127,204,0.4)';

        valueInYear(year: number) {
            const yearDifference = year - new Date().getFullYear();
            return yearDifference * context.monthlySavings * dividendYield()
        }
    }

    class ReinvestmentComponent extends Component {
        name = 'reinwestycja dywidend';
        borderColor = '#32cc65';
        backgroundColor = 'rgba(50,204,101,0.4)';

        valueInYear(year: number) {
            const yearDifference = year - new Date().getFullYear();
            return calculateCompoundInterest(
                earningsInYear(year),
                dividendYield(),
                12,
                yearDifference) - currentMonthlyEarnings()
        }
    }

    if (context.forecast && context.currencyRates) {
        return (
            // @ts-ignore
            <BarChart data={data()}/>
        );
    } else {
        return null;
    }


    function data() {
        const yearLabels = years();

        const components = [new BaseComponent(), new NewSavingsComponent(), new ReinvestmentComponent()]

        const datasets = components.map((component) => {
            return ({
                label: component.name,
                data: yearLabels.map(year => component.valueInYear(year)),
                backgroundColor: component.backgroundColor,
                borderColor: component.borderColor,
                borderWidth: 1,
            });
        });

        return {
            labels: yearLabels,
            datasets
        };
    }

    function years() {
        const currentYear = new Date().getFullYear();
        const years: number[] = [];

        for (let i = 0; i <= 12; i++) {
            years.push(currentYear + i);
        }

        return years;
    }

    function currentMonthlyEarnings() {
        return currentYearlyEarnings() / 12;
    }

    function currentYearlyEarnings() {
        if (context.forecast && context.currencyRates) {
            const amountInPln = new CurrencyConverter().inPln(context.forecast.yearlyForecast.total, context.currencyRates);
            return amountInPln * 0.81;
        }
        return 0;
    }

    function calculateCompoundInterest(
        principal: number,
        rate: number,
        timesCompounded: number,
        years: number
    ): number {
        if (principal <= 0 || rate <= 0 || timesCompounded <= 0 || years < 0) {
            console.log(years, principal, rate, timesCompounded)
            throw new Error("All inputs must be positive numbers.");
        }

        return principal * Math.pow(1 + rate / timesCompounded, timesCompounded * years);
    }

    function dividendYield() {
        const taxFactor = 0.81
        return 0.02 * taxFactor;
    }

    function earningsInYear(year: number) {
        const yearDifference = year - new Date().getFullYear();
        return currentMonthlyEarnings() + yearDifference * context.monthlySavings * dividendYield()
    }
}


export default FutureGainsBarChart;
