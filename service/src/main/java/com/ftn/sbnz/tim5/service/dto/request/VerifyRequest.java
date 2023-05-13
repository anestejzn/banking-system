package com.ftn.sbnz.tim5.service.dto.request;


import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ftn.sbnz.tim5.service.util.Constants.WRONG_SECURITY_CODE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyRequest {
    @NotBlank(message = WRONG_SECURITY_CODE)
    @NotNull(message = WRONG_SECURITY_CODE)
    private String verifyId;

    @NotNull(message = WRONG_SECURITY_CODE)
    @Positive(message = WRONG_SECURITY_CODE)
    private int securityCode;
}
