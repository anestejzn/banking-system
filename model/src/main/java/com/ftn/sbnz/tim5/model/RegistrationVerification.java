package com.ftn.sbnz.tim5.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDateTime;



@Entity
@Table(name="registration_verification")
@Getter
@Setter
@NoArgsConstructor
public class RegistrationVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name="security_code", nullable = false)
    protected String securityCode;

    @Column(name="failed_attempts", nullable = false)
    protected Integer failedAttempts = 0;

    @Column(name="user_email", nullable = false)
    protected String userEmail;

    @Column(name="expires", nullable = false)
    protected LocalDateTime expires;

    @Column(name="used", nullable = false)
    protected boolean used = false;

    @Column(name="salt", nullable = false)
    protected String salt;

    @Column(name = "hashedId", nullable = false)
    protected String hashedId;

    public RegistrationVerification(
            String securityCode,
            Integer failedAttempts,
            String userEmail,
            LocalDateTime expires,
            String salt,
            String hashedId
    ) {
        this.securityCode = securityCode;
        this.failedAttempts = failedAttempts;
        this.userEmail = userEmail;
        this.expires = expires;
        this.salt = salt;
        this.hashedId = hashedId;
    }
}