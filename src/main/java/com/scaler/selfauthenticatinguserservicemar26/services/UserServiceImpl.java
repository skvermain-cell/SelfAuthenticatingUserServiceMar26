package com.scaler.selfauthenticatinguserservicemar26.services;

import com.scaler.selfauthenticatinguserservicemar26.exceptions.InvalidTokenException;
import com.scaler.selfauthenticatinguserservicemar26.exceptions.PasswordMisMatchException;
import com.scaler.selfauthenticatinguserservicemar26.models.Token;
import com.scaler.selfauthenticatinguserservicemar26.models.User;
import com.scaler.selfauthenticatinguserservicemar26.repositories.TokenRepository;
import com.scaler.selfauthenticatinguserservicemar26.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepo,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenRepository tokenRepository) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }


    @Override
    public User signup(String name, String email, String password) {

        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        //BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));

        return userRepo.save(newUser);


    }

    @Override
    public Token login(String email, String password) throws PasswordMisMatchException {

        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            // login unsuccessful as password isn't matching
            throw new PasswordMisMatchException("Incorrect Password!");
        }

        //login successful and generate Token
        // some random long string can be generated as Token and for that we can use
        // Apache Commons Lang module which has been now included in the pom.xml
        Token token = new Token();
        token.setTokenValue(RandomStringUtils.randomAlphanumeric(128));
        token.setUser(user);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 30);
        Date expiryDt = cal.getTime();
        token.setExpiryAt(expiryDt);

        return tokenRepository.save(token);

    }

    @Override
    public User validateToken(String tokenValue) throws InvalidTokenException {

        Optional<Token> optionalToken = tokenRepository.findByTokenValueAndExpiryAtGreaterThan(
                tokenValue,
                new Date());
        if (optionalToken.isEmpty()) {
            throw new InvalidTokenException("Token has expired!");
        }

        Token token = optionalToken.get();

        return token.getUser();

    }


}
