package com.ftn.sbnz.tim5.service.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class RetireeRegistrationRequest extends ClientRegistrationRequest{

    @NotNull(message = "Terms of agreement for retirement must be selected.")
    private final boolean termsOfPIOFondAgreement;

    public RetireeRegistrationRequest(
            String email,
            String name,
            String surname,
            String password,
            String confirmPassword,
            String streetName,
            String streetNumber,
            String postCode,
            String city,
            LocalDateTime dateOfBirth,
            double monthlyIncome,
            boolean termsOfPIOFondAgreement,
            String accountTypeName
    ) {
        super(email, name, surname, password, confirmPassword, streetName, streetNumber, postCode, city, dateOfBirth, monthlyIncome, accountTypeName);
        this.termsOfPIOFondAgreement = termsOfPIOFondAgreement;
    }
}
