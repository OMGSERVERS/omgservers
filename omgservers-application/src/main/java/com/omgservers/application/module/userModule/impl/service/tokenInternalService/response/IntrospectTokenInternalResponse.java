package com.omgservers.application.module.userModule.impl.service.tokenInternalService.response;

import com.omgservers.application.module.userModule.model.user.UserTokenModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntrospectTokenInternalResponse {

    UserTokenModel tokenObject;
    long lifetime;
}
