package com.ftn.sbnz.tim5.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name="admin")
@Setter
@Getter
@NoArgsConstructor
public class Admin extends User{

    public Admin(String email, String password, String name, String surname){
        super(email, password, name, surname);
    }
}
