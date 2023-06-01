package com.ftn.sbnz.tim5.service.dto.request;

import com.ftn.sbnz.tim5.model.enums.EmployerStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.lang.annotation.After;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static com.ftn.sbnz.tim5.service.util.ErrorMessageConstants.*;

@Getter
@Setter
@NoArgsConstructor
public class EmployerRequest {

    private String name;
    private String pib;
    private LocalDateTime startedOperating;

    public EmployerRequest(String name, String pib, LocalDateTime startedOperating) {
        this.name = name;
        this.pib = pib;
        this.startedOperating = startedOperating;
    }
}
