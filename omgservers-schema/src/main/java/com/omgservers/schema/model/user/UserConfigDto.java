package com.omgservers.schema.model.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConfigDto {

    static public UserConfigDto create() {
        final var userConfig = new UserConfigDto();
        userConfig.setVersion(UserConfigVersionEnum.V1);
        return userConfig;
    }

    @NotNull
    UserConfigVersionEnum version;
}
