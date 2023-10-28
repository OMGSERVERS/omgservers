package com.omgservers.model.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenModel {

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
