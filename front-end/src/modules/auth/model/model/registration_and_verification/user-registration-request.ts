
export interface UserRegistrationRequest {
    email: string,
    name: string,
    surname: string,
    password: string,
    confirmPassword: string,
    streetName: string,
    streetNumber: string,
    postCode: string,
    city: string,
    dateOfBirth: Date,
    monthlyIncome: number,
    accountTypeName: string,
    termsOfPIOFondAgreement?: boolean,
    employerName?: string,
    startedWorking?: Date
}