package com.example.trainingsapp.wallets.model;

import com.example.trainingsapp.financialtransaktion.FinancialTransaction;
import com.example.trainingsapp.user.model.User;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wallets")

public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    @Column(name = "creation_date")
    private Instant creationDate;

    @OneToMany
    private List<FinancialTransaction> financialTransactionList = new ArrayList<>();

    public Wallet() {
    }

    public Wallet(String name, User user) {
        this.user = user;
        this.name = name;
        this.creationDate = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public List<FinancialTransaction> getFinancialTransactionList() {
        return financialTransactionList;
    }

    public void setFinancialTransactionList(List<FinancialTransaction> financialTransactionList) {
        this.financialTransactionList = financialTransactionList;
    }

    public void addFinancialTransakction(FinancialTransaction financialTransaction) {
        this.financialTransactionList.add(financialTransaction);
    }

    public void removeFinancialTransaction(FinancialTransaction financialTransaction) {
        this.financialTransactionList.remove(financialTransaction);
    }

}
