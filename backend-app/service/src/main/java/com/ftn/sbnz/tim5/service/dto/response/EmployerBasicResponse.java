package com.ftn.sbnz.tim5.service.dto.response;

import com.ftn.sbnz.tim5.model.Employer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

import static com.ftn.sbnz.tim5.service.util.Constants.PIO_FOND;

@Getter
@Setter
@NoArgsConstructor
public class EmployerBasicResponse {

    private Long id;
    private String name;

    public EmployerBasicResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public EmployerBasicResponse(Employer employer) {
        this.id = employer.getId();
        this.name = employer.getName();
    }

    public static List<EmployerBasicResponse> fromEmployersWithoutPioFondToBasicResponses(List<Employer> employers) {
        List<EmployerBasicResponse> responses = new LinkedList<>();
        employers.forEach(employer -> {
            if (!employer.getName().equalsIgnoreCase(PIO_FOND)) {
                responses.add(new EmployerBasicResponse(employer));
            }
        });

        return responses;
    }

}
