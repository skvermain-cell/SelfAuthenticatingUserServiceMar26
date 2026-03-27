package com.scaler.selfauthenticatinguserservicemar26.services;

import com.scaler.selfauthenticatinguserservicemar26.exceptions.InvalidTokenException;
import com.scaler.selfauthenticatinguserservicemar26.exceptions.PasswordMisMatchException;
import com.scaler.selfauthenticatinguserservicemar26.models.Token;
import com.scaler.selfauthenticatinguserservicemar26.models.User;

public interface UserService {

    User signup(String name, String email, String password);

    Token login(String email, String password) throws PasswordMisMatchException;

    User validateToken(String tokenValue) throws InvalidTokenException;

}
