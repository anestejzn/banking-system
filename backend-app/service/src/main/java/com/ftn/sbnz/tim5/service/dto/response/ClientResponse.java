package com.ftn.sbnz.tim5.service.dto.response;

import com.ftn.sbnz.tim5.model.*;
import com.ftn.sbnz.tim5.model.enums.EmployeeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClientResponse extends UserResponse {

    private String streetName;
    private String streetNumber;
    private String postCode;
    private String city;
    private LocalDateTime dateOfBirth;
    private EmployeeStatus employeeStatus;
    private double monthlyIncome;

    public ClientResponse(Long id,
                          String email,
                          String name,
                          String surname,
                          Role role,
                          String streetName,
                          String streetNumber,
                          String postCode,
                          String city,
                          LocalDateTime dateOfBirth,
                          EmployeeStatus employeeStatus,
                          double monthlyIncome
    ) {
        super(id, email, "", name, surname, role);
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postCode = postCode;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.employeeStatus = employeeStatus;
        this.monthlyIncome = monthlyIncome;
    }

    public ClientResponse(Client client) {
        super(client.getId(), client.getEmail(), "", client.getName(), client.getSurname(), client.getRole());
        this.streetName = client.getStreetName();
        this.streetNumber = client.getStreetNumber();
        this.postCode = client.getPostCode();
        this.city = client.getCity();
        this.dateOfBirth = client.getDateOfBirth();
        this.employeeStatus = client.getEmployeeStatus();
        this.monthlyIncome = client.getMonthlyIncome();
    }

    public static List<ClientResponse> fromClientListToClientResponses(List<Client> clients) {
        List<ClientResponse> responses = new LinkedList<>();
        clients.forEach(client -> {
            responses.add(new ClientResponse(client));
        });

        return responses;
    }
}
