package com.omgservers.model.user;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenModel {

    static public void validate(UserTokenModel token) {
        if (token == null) {
            throw new ServerSideBadRequestException("token is null");
        }
    }

    Long id;
    Long userId;
    UserRoleEnum role;
    @ToString.Exclude
    long secret;
}
