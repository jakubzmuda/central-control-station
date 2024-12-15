import React from "react";
import {Bar} from "react-chartjs-2";
import {BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip,} from 'chart.js';


ChartJS.register(CategoryScale, LinearScale, BarElement, Legend, Title, Tooltip);

function MonthlyBarChart() {
    return (
        // @ts-ignore
        <Bar data={data()} options={options()}/>
    );

    function data() {
        const labels = months();
        return {
            labels: labels,
            datasets: [
                {
                    label: 'Product A',
                    data: [300, 500, 700, 400, 600],
                    backgroundColor: 'rgba(75, 192, 192, 0.6)',
                    stack: 'stack1', // Grouping all datasets into one stack
                },
                {
                    label: 'Product B',
                    data: [200, 400, 600, 300, 500],
                    backgroundColor: 'rgba(153, 102, 255, 0.6)',
                    stack: 'stack1', // Same stack group
                },
                {
                    label: 'Product C',
                    data: [100, 300, 400, 200, 300],
                    backgroundColor: 'rgba(255, 159, 64, 0.6)',
                    stack: 'stack1', // Same stack group
                },
            ],
        };
    }

    function options() {
        return {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false,
                },
                title: {
                    display: false,
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
                        display: false,
                    },
                    grid: {
                        display: false,
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

    function months() {
        return Array.from({length: 12}, (_, i) =>
            new Date(0, i).toLocaleString('en-US', {month: 'long'}).toLowerCase()
        );
    }
}


export default MonthlyBarChart;
