package com.ftn.sbnz.tim5.service.dto.request;

import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

import static com.ftn.sbnz.tim5.service.util.Constants.*;
import static com.ftn.sbnz.tim5.service.util.ErrorMessageConstants.EMPTY_EMAIL;
import static com.ftn.sbnz.tim5.service.util.ErrorMessageConstants.TOO_LONG_EMAIL;
import static com.ftn.sbnz.tim5.service.util.ErrorMessageConstants.WRONG_PASSWORD;

@Getter
@Setter
public class ClientRegistrationRequest {

    @NotBlank(message = EMPTY_EMAIL)
    @Size(max = 320, message = TOO_LONG_EMAIL)
    private final String email;

    @NotBlank(message = WRONG_NAME)
    @Pattern(regexp = LEGIT_NAME_REG, message = WRONG_NAME)
    private final String name;

    @NotBlank(message = WRONG_SURNAME)
    @Pattern(regexp = LEGIT_NAME_REG, message = WRONG_SURNAME)
    private final String surname;

    @NotBlank(message = WRONG_PASSWORD)
    @Pattern(regexp = LEGIT_PASSWORD_REG, message = WRONG_PASSWORD)
    private final String password;

    @NotBlank(message = WRONG_PASSWORD)
    @Pattern(regexp = LEGIT_PASSWORD_REG, message = WRONG_PASSWORD)
    private final String confirmPassword;

    @NotBlank(message = WRONG_ADDRESS)
    @Pattern(regexp = LEGIT_NAME_REG, message = WRONG_COUNTRY)
    private final String streetName;

    @NotBlank(message = WRONG_ADDRESS)
    private final String streetNumber;

    @NotBlank(message = LEGIT_NAME_REG)
    private final String postCode;

    @NotBlank(message = WRONG_CITY)
    @Pattern(regexp = LEGIT_RE_CITY_AND_STREET_REG, message = WRONG_CITY)
    private final String city;

    @NotNull(message = WRONG_DOB)
    private final LocalDateTime dateOfBirth;

    @NotNull(message = WRONG_INCOME)
    @Positive(message = WRONG_INCOME)
    private final double monthlyIncome;

    @NotBlank(message = WRONG_ACC_TYPE_NAME)
    private final String accountTypeName;

    public ClientRegistrationRequest(String email,
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
                                     String accountTypeName
    ) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postCode = postCode;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.monthlyIncome = monthlyIncome;
        this.accountTypeName = accountTypeName;
    }
}
