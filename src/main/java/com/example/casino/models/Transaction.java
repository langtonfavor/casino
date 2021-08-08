package com.example.casino.models;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Transaction {
    private Long playerId;
    @Id
    private Long transactionId;
    private BigDecimal amount;
    @JsonIgnore
    private TransactionType transactionType;

    public Transaction(){}
}
