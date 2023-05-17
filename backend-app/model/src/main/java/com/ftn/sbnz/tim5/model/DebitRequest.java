package com.ftn.sbnz.tim5.model;

import com.ftn.sbnz.tim5.model.enums.CardType;
import com.ftn.sbnz.tim5.model.enums.DebitType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="debit_request")
@Setter
@Getter
@NoArgsConstructor
public class DebitRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="debit_type", nullable = false)
    private DebitType debitType;
    @Column(name="card_type")
    private CardType cardType;
    @Column(name="total_amount", nullable=false)
    private double totalAmount;
}
