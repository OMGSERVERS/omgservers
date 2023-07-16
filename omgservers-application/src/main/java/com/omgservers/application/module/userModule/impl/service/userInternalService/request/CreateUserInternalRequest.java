package com.omgservers.application.module.userModule.impl.service.userInternalService.request;

import com.omgservers.application.module.userModule.model.user.UserModel;
import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserInternalRequest implements InternalRequest {

    static public void validate(CreateUserInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UserModel user;

    @Override
    public String getRequestShardKey() {
        return user.getUuid().toString();
    }
}
