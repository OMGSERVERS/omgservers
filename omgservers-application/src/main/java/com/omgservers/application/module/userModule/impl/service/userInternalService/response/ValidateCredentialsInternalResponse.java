package com.omgservers.application.module.userModule.impl.service.userInternalService.response;

import com.omgservers.application.module.userModule.model.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCredentialsInternalResponse {

    UserModel user;
}
