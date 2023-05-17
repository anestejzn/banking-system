package com.ftn.sbnz.tim5.service.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;

import static com.ftn.sbnz.tim5.service.util.Constants.WRONG_EMPLOYER_NAME;

@Getter
@Setter
public class EmployedClientRegistrationRequest extends ClientRegistrationRequest{

    @NotBlank(message = WRONG_EMPLOYER_NAME)
    private final String employerName;

    @NotBlank(message = "Started working date must be selected.")
    private final LocalDateTime startedWorking;

    public EmployedClientRegistrationRequest(String email,
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
                                             String accountTypeName,
                                             String employerName,
                                             LocalDateTime startedWorking
    ) {
        super(email, name, surname, password, confirmPassword, streetName, streetNumber, postCode, city, dateOfBirth, monthlyIncome, accountTypeName);
        this.employerName = employerName;
        this.startedWorking = startedWorking;
    }
}
