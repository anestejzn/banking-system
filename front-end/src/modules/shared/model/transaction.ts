

export interface Transaction {
    id: number;
    amount: number;
    transactionDate: number;
    income: boolean;
    otherSide: string;
    transactionType: string;
    status?: string;
    boughtCardType?: string;
}