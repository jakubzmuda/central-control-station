import React from "react";
import {Bar} from "react-chartjs-2";
import {BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip,} from 'chart.js';


ChartJS.register(CategoryScale, LinearScale, BarElement, Legend, Title, Tooltip);

function BarChart({data}: {data: any}) {

    return (
        // @ts-ignore
        <Bar data={data} options={options()}/>
    );

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

export default BarChart;
