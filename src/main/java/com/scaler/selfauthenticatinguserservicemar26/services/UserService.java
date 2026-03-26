package com.scaler.selfauthenticatinguserservicemar26.services;

import com.scaler.selfauthenticatinguserservicemar26.models.User;

public interface UserService {

    User signup(String name, String email, String password);

}
