export interface Portfolio {
    entries: PortfolioEntry[]
}

export interface PortfolioEntry {
    productTicker: string;
    amount: number;
}