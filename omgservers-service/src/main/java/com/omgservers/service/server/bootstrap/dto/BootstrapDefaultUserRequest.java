package com.omgservers.service.server.bootstrap.dto;

import com.omgservers.schema.model.user.UserRoleEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BootstrapDefaultUserRequest {

    @NotNull
    String alias;

    @NotNull
    String password;

    @NotNull
    UserRoleEnum role;
}
