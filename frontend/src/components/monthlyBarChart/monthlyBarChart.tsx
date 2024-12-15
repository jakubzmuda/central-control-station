import React from "react";
import {Bar} from "react-chartjs-2";
import {BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip,} from 'chart.js';


ChartJS.register(CategoryScale, LinearScale, BarElement, Legend, Title, Tooltip);

function MonthlyBarChart() {
    return (
        // @ts-ignore
        <Bar data={data()} options={options()} plugins={[stackSumPlugin]}/>
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
                    stack: 'stack1',
                },
                {
                    label: 'Product B',
                    data: [200, 400, 600, 300, 500],
                    backgroundColor: 'rgba(153, 102, 255, 0.6)',
                    stack: 'stack1',
                },
                {
                    label: 'Product C',
                    data: [100, 300, 400, 200, 300],
                    backgroundColor: 'rgba(255, 159, 64, 0.6)',
                    stack: 'stack1',
                },
                {
                    label: 'Product D',
                    data: [100, 300, 400, 200, 300],
                    backgroundColor: 'rgba(67,45,23,0.6)',
                    stack: 'stack1',
                },
                {
                    label: 'Product E',
                    data: [100, 300, 400, 200, 300],
                    backgroundColor: 'rgba(207,26,94,0.6)',
                    stack: 'stack1',
                },
                {
                    label: 'Product F',
                    data: [100, 300, 400, 200, 300],
                    backgroundColor: 'rgba(0,118,253,0.6)',
                    stack: 'stack1',
                },
                {
                    label: 'Product G',
                    data: [100, 300, 400, 200, 300],
                    backgroundColor: 'rgba(34,255,0,0.6)',
                    stack: 'stack1',
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

const stackSumPlugin = {
    id: 'stackSumPlugin',
    beforeDraw(chart) {
        const { ctx, scales: { x, y } } = chart;

        chart.data.labels.forEach((label, index) => {
            let total = 0;

            chart.data.datasets.forEach((dataset) => {
                total += dataset.data[index] || 0;
            });

            const xPosition = x.getPixelForValue(index);
            const yPosition = y.getPixelForValue(total);

            if(total > 0) {
                ctx.save();
                ctx.fillStyle = '#fff';
                ctx.font = '9px Arial';
                ctx.textAlign = 'center';
                ctx.fillText(`${total} zł`, xPosition, yPosition - 12);
                ctx.restore();
            }
        });
    },
};


export default MonthlyBarChart;
