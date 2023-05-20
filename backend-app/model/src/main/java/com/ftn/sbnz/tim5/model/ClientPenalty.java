package com.ftn.sbnz.tim5.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Entity
@Table(name="client_penalty")
@Setter
@Getter
@NoArgsConstructor
@Role(Role.Type.EVENT)
@Timestamp("timeStamp")
@Expires("28d")
public class ClientPenalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_stamp", nullable = false)
    private Date timeStamp;

    @Column(name = "message", nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public ClientPenalty(Date timeStamp, String message) {
        this.timeStamp = timeStamp;
        this.message = message;
    }
}
