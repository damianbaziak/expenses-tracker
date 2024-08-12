package com.example.trainingsapp.financialtransaktion;

import com.example.trainingsapp.wallet.model.Wallet;
import jakarta.persistence.*;

@Entity
@Table(name = "financial_transactions")

public class FinancialTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;

    private String name;

}


