package com.example.trainingsapp.wallets;

import jakarta.persistence.*;

@Entity
@Table(name = "wallets")

public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@JoinColumn(name = "user_id")
    //private User user;

}
