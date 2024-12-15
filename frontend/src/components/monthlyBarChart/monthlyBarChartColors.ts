export class MonthlyBarChartColors {

    private colors: Color[] = [
        {background: 'rgba(65,255,80,0.2)', border: 'rgb(119,255,65)'},
        {background: 'rgba(255,230,65,0.2)', border: 'rgb(255,230,65)'},
        {background: 'rgba(65,131,255,0.2)', border: 'rgb(65,131,255)'},
        {background: 'rgba(255,65,179,0.2)', border: 'rgb(255,65,179)'},
        {background: 'rgba(65,255,239,0.2)', border: 'rgb(65,255,239)'},
        {background: 'rgba(255,173,65,0.2)', border: 'rgb(255,173,65)'},
        {background: 'rgba(255,65,65,0.2)', border: 'rgb(255,65,65)'},
        {background: 'rgba(255,65,65,0.2)', border: 'rgb(119,65,255)'},
        {background: 'rgba(179,255,65,0.2)', border: 'rgb(179,255,65)'},
        {background: 'rgba(255,122,65,0.2)', border: 'rgb(255,122,65)'},
    ].reverse()

    next() {
        return this.colors.pop()
    }
}

export type Color = {
    background: string,
    border: string
}