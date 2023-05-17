
export interface AccountType {
    id: number,
    name: string,
    currency: string,
    monthlySubscription: number,
    overdraft: boolean,
    cashCreditLimit: number,
    cards: string[]
}