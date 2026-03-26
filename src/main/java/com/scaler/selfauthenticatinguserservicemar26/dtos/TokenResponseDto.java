package com.scaler.selfauthenticatinguserservicemar26.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TokenResponseDto {

    private String tokenValue;
    private Date expiryAt;
    private String email;
}
