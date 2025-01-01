import React, {useContext} from "react";
import {Bar} from "react-chartjs-2";
import {BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip,} from 'chart.js';
import {MonthlyBarChartColors} from "./monthlyBarChartColors";
import {YearlyForecast} from "../../types/types";
import {AppContext} from "../../context/context";
import {CurrencyConverter} from "../../currency/currencyConverter";


ChartJS.register(CategoryScale, LinearScale, BarElement, Legend, Title, Tooltip);

function MonthlyBarChart({forecast}: { forecast: YearlyForecast }) {

    const context = useContext(AppContext);

    return (
        // @ts-ignore
        <Bar data={data(forecast)} options={options()}/>
    );

    function data(forecast: YearlyForecast) {
        const labels = Object.keys(forecast.months);

        const productDataMap: { [product: string]: number[] } = {};

        Object.entries(forecast.months).forEach(([monthKey, monthData]) => {
            Object.values(monthData.distributions.sort((dist1, dist2) => dist2.monetaryValue["USD"] - dist1.monetaryValue["USD"])).forEach((distribution) => {
                if (!productDataMap[distribution.product]) {
                    productDataMap[distribution.product] = Array(labels.length).fill(0);
                }

                const monthIndex = labels.indexOf(monthKey);
                productDataMap[distribution.product][monthIndex] = new CurrencyConverter().inPln(distribution.monetaryValue, context.currencyRates)
            });
        });

        const barChartColors = new MonthlyBarChartColors();

        const datasets = Object.entries(productDataMap).map(([product, data], index) => {
            const colors = barChartColors.next();
            return ({
                label: product,
                data,
                backgroundColor: colors?.background,
                borderColor: colors?.border,
                borderWidth: 1,
            });
        });

        return {
            labels,
            datasets
        };
    }

    function options() {
        return {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: true,
                    position: 'bottom',
                    labels: {
                        color: '#fff',
                        font: {
                            size: 14,
                        },
                        boxWidth: 20,
                        padding: 10,
                    },
                    title: {
                        display: false,
                    },
                },
            },
            scales: {
                x: {
                    stacked: true,
                    ticks: {
                        color: '#fff',
                    },
                    grid: {
                        display: false,
                        drawBorder: false,
                    },
                },
                y: {
                    stacked: true,
                    ticks: {
                        display: true,
                        color: '#fff',
                    },
                    grid: {
                        display: true,
                        color: 'rgba(255,255,255,0.1)',
                        drawBorder: false,
                    },
                },
            },
            layout: {
                padding: {
                    left: 0, right: 0, top: 0, bottom: 0,
                },
            },
        };
    }
}

export default MonthlyBarChart;
