package com.omgservers.application.module.userModule.model.user;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(exclude = {"rawToken"})
public class UserTokenContainerModel {

    static public void validateUserTokenContainerModel(UserTokenContainerModel userTokenContainerModel) {
        if (userTokenContainerModel == null) {
            throw new ServerSideBadRequestException("tokenContainer is null");
        }
        UserTokenModel.validateUserTokenModel(userTokenContainerModel.getTokenObject());
        validateRawToken(userTokenContainerModel.getRawToken());
    }

    static public void validateRawToken(String rawToken) {
        if (rawToken == null) {
            throw new ServerSideBadRequestException("rawToken field is null");
        }
        if (rawToken.isBlank()) {
            throw new ServerSideBadRequestException("rawToken string is blank");
        }
        if (rawToken.length() > 1024) {
            throw new ServerSideBadRequestException("rawToken string is too long");
        }
    }

    final UserTokenModel tokenObject;
    final String rawToken;
    final long lifetime;
}
