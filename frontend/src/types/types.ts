export interface Portfolio {
    entries: PortfolioEntry[]
}

export interface PortfolioEntry {
    productTicker: string;
    amount: number;
}

export interface YearlyForecast {
    total: MonetaryValue
}

export type MonetaryValue = { [currency: string]: number }