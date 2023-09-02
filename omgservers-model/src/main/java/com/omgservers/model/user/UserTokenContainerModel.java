package com.omgservers.model.user;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class UserTokenContainerModel {

    static public void validate(UserTokenContainerModel userTokenContainerModel) {
        if (userTokenContainerModel == null) {
            throw new ServerSideBadRequestException("tokenContainer is null");
        }
        UserTokenModel.validate(userTokenContainerModel.getTokenObject());
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

    UserTokenModel tokenObject;
    @ToString.Exclude
    String rawToken;
    long lifetime;
}
