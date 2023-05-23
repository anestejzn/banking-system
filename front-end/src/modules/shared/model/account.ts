import { AccountType } from "./account-type";
import { Debit } from "./debit";
import { Transaction } from "./transaction";

export interface Account {
    id: number;
    accountNumber: string;
    accountDate: Date;
    totalBalance: number;
    accountType: AccountType;
    cards: string[];
    transactions?: Transaction[];
    debits?: Debit[];
}