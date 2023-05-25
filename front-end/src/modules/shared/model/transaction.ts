

export interface Transaction {
    id: number;
    amount: number;
    transactionDate: Date;
    income: boolean;
    otherSide: string;
    transactionType: string;
    status?: string;
    boughtCardType?: string;
}