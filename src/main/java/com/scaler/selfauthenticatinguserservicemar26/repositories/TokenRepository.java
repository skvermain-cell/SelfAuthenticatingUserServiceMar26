package com.scaler.selfauthenticatinguserservicemar26.repositories;

import com.scaler.selfauthenticatinguserservicemar26.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByTokenValue(String tokenValue);

    Token save(Token token);

    Optional<Token> findByTokenValueAndExpiryAtGreaterThan(String tokenValue,
                                                           Date expiryAt);
}
