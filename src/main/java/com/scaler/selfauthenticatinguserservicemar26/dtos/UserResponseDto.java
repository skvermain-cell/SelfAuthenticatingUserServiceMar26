package com.scaler.selfauthenticatinguserservicemar26.dtos;


import com.scaler.selfauthenticatinguserservicemar26.models.Roles;
import com.scaler.selfauthenticatinguserservicemar26.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserResponseDto {

    private Long userId;
    private String email;
    private List<Roles> userRoles;

    public static UserResponseDto from(User user) {
        if (user == null) {
            return null;
        }

        UserResponseDto userRespDto = new UserResponseDto();
        userRespDto.setUserId(user.getId());
        userRespDto.setEmail(user.getEmail());
        userRespDto.setUserRoles(user.getUserRoles());

        return userRespDto;
    }

}
