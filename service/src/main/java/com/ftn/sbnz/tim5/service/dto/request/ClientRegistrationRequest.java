package com.ftn.sbnz.tim5.service.dto.request;

import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


import static com.ftn.sbnz.tim5.service.util.Constants.*;
import static com.ftn.sbnz.tim5.service.util.ErrorMessageConstants.EMPTY_EMAIL;
import static com.ftn.sbnz.tim5.service.util.ErrorMessageConstants.TOO_LONG_EMAIL;
import static com.ftn.sbnz.tim5.service.util.ErrorMessageConstants.WRONG_PASSWORD;

@Getter
@Setter
public class ClientRegistrationRequest {
    @NotNull
    @NotBlank(message = EMPTY_EMAIL)
    @Size(max = 320, message = TOO_LONG_EMAIL)
    private final String email;

    @NotNull
    @NotBlank(message = WRONG_NAME)
    @Pattern(regexp = LEGIT_NAME_REG, message = WRONG_NAME)
    private final String name;

    @NotNull
    @NotBlank(message = WRONG_SURNAME)
    @Pattern(regexp = LEGIT_NAME_REG, message = WRONG_SURNAME)
    private final String surname;

    @NotBlank(message = WRONG_PASSWORD)
    @Pattern(regexp = LEGIT_PASSWORD_REG, message = WRONG_PASSWORD)
    private final String password;

    @NotBlank(message = WRONG_PASSWORD)
    @Pattern(regexp = LEGIT_PASSWORD_REG, message = WRONG_PASSWORD)
    private final String confirmPassword;

    @NotNull(message = WRONG_ROLE)
    private final String role;

//    @NotNull
//    @NotBlank
//    @Pattern(regexp = LEGIT_COUNTRY_REG, message = WRONG_COUNTRY)
//    private final String country;
//
//    @NotNull
//    @NotBlank
//    @Pattern(regexp = LEGIT_COUNTRY_REG, message = WRONG_CITY)
//    private final String city;

    public ClientRegistrationRequest(
            String email,
            String name,
            String surname,
//            String country,
//            String city
            String password, String confirmPassword, String role) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
//        this.country = country;
//        this.city = city;
        this.confirmPassword = confirmPassword;
        this.role = role;
    }
}
