export interface Portfolio {
    entries: PortfolioEntry[]
}

export interface PortfolioEntry {
    productTicker: string;
    amount: number;
}

export interface YearlyForecast {
    total: MonetaryValue,
    months: MonthlyForecast
}

export interface MonthlyForecast {
    [month: string]: DistributionList,
}

export interface DistributionList {
    total: MonetaryValue,
    distributions: Distribution[]
}

export interface Distribution {
    product: string;
    monetaryValue: MonetaryValue
}

export type MonetaryValue = { [currency: string]: number }