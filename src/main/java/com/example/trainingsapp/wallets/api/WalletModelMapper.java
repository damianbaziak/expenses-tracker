package com.example.trainingsapp.wallets.api;

import com.example.trainingsapp.wallets.api.dto.WalletDTO;
import com.example.trainingsapp.wallets.model.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WalletModelMapper {

    @Mapping(source = "user.id", target = "userId")
    WalletDTO mapWalletEntityToWalletDTO(Wallet wallet);
}
