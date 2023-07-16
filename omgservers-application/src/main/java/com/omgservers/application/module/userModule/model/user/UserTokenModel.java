package com.omgservers.application.module.userModule.model.user;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenModel {

    static public void validateUserTokenModel(UserTokenModel token) {
        if (token == null) {
            throw new ServerSideBadRequestException("token is null");
        }
    }

    UUID uuid;
    UUID user;
    UserRoleEnum role;
    @ToString.Exclude
    long secret;
}
