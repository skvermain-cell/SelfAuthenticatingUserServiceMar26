package com.scaler.selfauthenticatinguserservicemar26.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Token extends BaseModel{

    private String tokenValue;

    @ManyToOne
    private User user;
    private Date expiryAt;
}
