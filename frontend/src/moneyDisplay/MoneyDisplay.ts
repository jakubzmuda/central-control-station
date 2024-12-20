export class MoneyDisplay {
    asString(amount: number) {
        if (amount > 100) {
            return this.formatNumberWithSpaces(Math.round(amount))
        }
        const formatted = amount.toFixed(2);
        return this.formatNumberWithSpaces(formatted.endsWith(".00") ? formatted.slice(0, -3) : formatted)
    }

    private formatNumberWithSpaces(num: number | string): string {
        return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
    }
}