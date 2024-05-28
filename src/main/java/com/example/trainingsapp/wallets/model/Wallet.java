package com.example.trainingsapp.wallets.model;

import com.example.trainingsapp.user.model.User;
import jakarta.persistence.*;

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
    private String creationDate;

}
