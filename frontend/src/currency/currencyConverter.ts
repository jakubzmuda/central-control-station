import {CurrencyRates, MonetaryValue} from "../types/types";

export class CurrencyConverter {

    inPln(monetaryValue: MonetaryValue, currencyRates: CurrencyRates): number {
        const amountInPln = monetaryValue["PLN"] || 0;
        const amountInUsd = monetaryValue["USD"] || 0;
        const amountInEur = monetaryValue["EUR"] || 0;

        return amountInPln + amountInUsd * currencyRates["USD/PLN"] + amountInEur * currencyRates["EUR/PLN"];
    }
}