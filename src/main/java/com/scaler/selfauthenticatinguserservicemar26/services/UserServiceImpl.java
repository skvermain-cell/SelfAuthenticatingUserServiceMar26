package com.scaler.selfauthenticatinguserservicemar26.services;

import com.scaler.selfauthenticatinguserservicemar26.exceptions.InvalidTokenException;
import com.scaler.selfauthenticatinguserservicemar26.exceptions.PasswordMisMatchException;
import com.scaler.selfauthenticatinguserservicemar26.models.Token;
import com.scaler.selfauthenticatinguserservicemar26.models.User;
import com.scaler.selfauthenticatinguserservicemar26.repositories.TokenRepository;
import com.scaler.selfauthenticatinguserservicemar26.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;
    private SecretKey secretKey;

    public UserServiceImpl(UserRepository userRepo,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenRepository tokenRepository, SecretKey secretKey) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.secretKey = secretKey;
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
    public String login(String email, String password) throws PasswordMisMatchException {

        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            // login unsuccessful as password isn't matching
            throw new PasswordMisMatchException("Incorrect Password!");
        }

        /* This piece of code was using Apache Comms for generating random Token, which has now been
        commented as we will now be using JWT
        //login successful and generate Token
        // some random long string can be generated as Token and for that we can use
        // Apache Commons Lang module which has been now included in the pom.xml
        Token token = new Token();
        token.setTokenValue(RandomStringUtils.randomAlphanumeric(128));
        token.setUser(user);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 30);
        Date expiryDt = cal.getTime();
        token.setExpiryAt(expiryDt); */

        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "scaler.com");
        claims.put("userId", user.getId());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 30);
        Date expiryDt = cal.getTime();

        claims.put("expiryDt", expiryDt);
        claims.put("roles", user.getUserRoles());

        String jwtToken = Jwts.builder().claims(claims).signWith(secretKey).compact();

        return jwtToken;

    }

    @Override
    public User validateToken(String tokenValue) throws InvalidTokenException {

        /* commenting this code now as it was used to validate the token generated through Apache commons and stored in DB
        now we will be using the JWT and validate that JWt

        Optional<Token> optionalToken = tokenRepository.findByTokenValueAndExpiryAtGreaterThan(
                tokenValue,
                new Date());
        if (optionalToken.isEmpty()) {
            throw new InvalidTokenException("Token has expired!");
        }

        Token token = optionalToken.get();
        return token.getUser();

        */

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(tokenValue).getPayload();

        Long expiryDateFromToken = (Long) claims.get("expiryDt");
        Instant instant = Instant.ofEpochMilli(expiryDateFromToken);
        java.util.Date expiryDate = java.util.Date.from(instant);

        Date currentDt = new Date();

        if (expiryDate.before(currentDt)) {
            throw new InvalidTokenException("Invalid Token! Please login again.");
        }

        //Long userId = (Long) claims.get("userId");
        Long userId = claims.get("userId", Long.class);
        Optional<User> optionalUser = userRepo.findById(userId);


        return optionalUser.get();

    }


}
