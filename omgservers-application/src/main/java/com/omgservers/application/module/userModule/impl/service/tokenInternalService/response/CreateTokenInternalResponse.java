package com.omgservers.application.module.userModule.impl.service.tokenInternalService.response;

import com.omgservers.application.module.userModule.model.user.UserTokenModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenInternalResponse {

    UserTokenModel tokenObject;
    @ToString.Exclude
    String rawToken;
    long lifetime;
}
