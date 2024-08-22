package com.example.trainingsapp.wallet.api;

import com.example.trainingsapp.wallet.api.dto.WalletDTO;
import com.example.trainingsapp.wallet.api.model.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WalletModelMapper {

    @Mapping(source = "user.id", target = "userId")
    WalletDTO mapWalletEntityToWalletDTO(Wallet wallet);
}
