package com.ftn.sbnz.tim5.service.dto.response;

import com.ftn.sbnz.tim5.model.Role;
import com.ftn.sbnz.tim5.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Role role;

    public UserResponse(Long id, String email, String password, String name, String surname, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public UserResponse(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.role = user.getRole();
    }
}
