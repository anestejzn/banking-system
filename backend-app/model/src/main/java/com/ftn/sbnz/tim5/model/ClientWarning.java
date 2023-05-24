package com.ftn.sbnz.tim5.model;

import com.ftn.sbnz.tim5.model.enums.WarningType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="client_warning")
@Setter
@Getter
@NoArgsConstructor
@org.kie.api.definition.type.Role(Role.Type.EVENT)
@Timestamp("timeStamp")
@Expires("12m")
public class ClientWarning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_stamp", nullable = false)
    private Date timeStamp;

    @Column(name = "warning_type", nullable = false)
    private WarningType warningType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public ClientWarning(Date timeStamp, WarningType warningType) {
        this.timeStamp = timeStamp;
        this.warningType = warningType;
    }
}

