package com.scaler.selfauthenticatinguserservicemar26.dtos;

import com.scaler.selfauthenticatinguserservicemar26.models.Token;
import com.scaler.selfauthenticatinguserservicemar26.repositories.TokenRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TokenResponseDto {

    private String tokenValue;
    private Date expiryAt;
    private String email;

    public static TokenResponseDto from(Token token) {

        if (token == null) {
            return null;
        }

        TokenResponseDto tokenResponseDto = new TokenResponseDto();
        tokenResponseDto.setTokenValue(token.getTokenValue());
        tokenResponseDto.setEmail(token.getUser().getEmail());
        tokenResponseDto.setExpiryAt(token.getExpiryAt());

        return tokenResponseDto;

    }
}
