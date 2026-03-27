package com.scaler.selfauthenticatinguserservicemar26.controllers;

import com.scaler.selfauthenticatinguserservicemar26.dtos.LoginRequestDto;
import com.scaler.selfauthenticatinguserservicemar26.dtos.TokenResponseDto;
import com.scaler.selfauthenticatinguserservicemar26.dtos.SignUpRequestDto;
import com.scaler.selfauthenticatinguserservicemar26.dtos.UserResponseDto;
import com.scaler.selfauthenticatinguserservicemar26.exceptions.InvalidTokenException;
import com.scaler.selfauthenticatinguserservicemar26.exceptions.PasswordMisMatchException;
import com.scaler.selfauthenticatinguserservicemar26.models.Token;
import com.scaler.selfauthenticatinguserservicemar26.models.User;
import com.scaler.selfauthenticatinguserservicemar26.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userSerivce;

    public UserController(UserService userSerivce) {
        this.userSerivce = userSerivce;
    }

    @PostMapping("/signup")
    public UserResponseDto signup(@RequestBody SignUpRequestDto request) {

        User user = userSerivce.signup  (
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        return UserResponseDto.from(user);
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody LoginRequestDto request) throws PasswordMisMatchException {

        Token token = userSerivce.login(request.getEmail(), request.getPassword());

        return TokenResponseDto.from(token);


    }

    @GetMapping("/validate/{tokenValue}")
    public UserResponseDto validateToken(@PathVariable String tokenValue) throws InvalidTokenException {

        User user = userSerivce.validateToken(tokenValue);

        return UserResponseDto.from(user);

    }

    //public logOut() {
    //
    //}

}
