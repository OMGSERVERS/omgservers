package com.omgservers.application.module.userModule.impl.service.tokenInternalService.request;

import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenInternalRequest implements InternalRequest {

    static public void validate(CreateTokenInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UUID user;
    @ToString.Exclude
    String password;

    @Override
    public String getRequestShardKey() {
        return user.toString();
    }
}
