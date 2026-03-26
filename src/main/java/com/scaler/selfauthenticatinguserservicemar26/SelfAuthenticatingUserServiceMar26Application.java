package com.scaler.selfauthenticatinguserservicemar26;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SelfAuthenticatingUserServiceMar26Application {

    public static void main(String[] args) {
        SpringApplication.run(SelfAuthenticatingUserServiceMar26Application.class, args);
    }

}
