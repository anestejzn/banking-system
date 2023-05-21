export interface Debit{
    id: number;
    debitType: string;
    totalAmount: number;
    debitDate: Date;
    monthlyInterest: number;
    monthlyAmount: number;
    paymentPeriod: number;
    debitStatus: string;
    accountId: number;
}