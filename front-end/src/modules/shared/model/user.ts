
export interface Role {
  id: number,
  roleName: string
}

export interface User {
  id: number;
  email: string;
  name: string;
  surname: string;
  country: string;
  city: string;
  accountStatus: string;
  role: Role;
}

export interface Client {
  id: number;
  email: string;
  name: string;
  surname: string;
  accountStatus: string;
  role: Role;
  streetName: string;
  streetNumber: string;
  postCode: string;
  city: string,
  dateOfBirth: string;
  employeeStatus: string;
  monthlyIncome: number;
  accountId?: number;
}
 