package com.omgservers.application.module.userModule.impl.service.clientInternalService.request;

import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetClientInternalRequest implements InternalRequest {

    static public void validate(GetClientInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UUID user;
    UUID client;

    @Override
    public String getRequestShardKey() {
        return user.toString();
    }
}
