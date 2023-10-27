package com.omgservers.model.user;

import com.omgservers.exception.ServerSideBadRequestException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenModel {

    public static void validate(UserTokenModel token) {
        if (token == null) {
            throw new ServerSideBadRequestException("token is null");
        }
    }

    @NotNull
    Long id;

    @NotNull
    Long userId;

    @NotNull
    UserRoleEnum role;

    @NotNull
    @ToString.Exclude
    long secret;
}
