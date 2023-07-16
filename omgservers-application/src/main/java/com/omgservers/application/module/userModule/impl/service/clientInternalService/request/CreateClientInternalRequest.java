package com.omgservers.application.module.userModule.impl.service.clientInternalService.request;

import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientInternalRequest implements InternalRequest {

    static public void validate(CreateClientInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UUID user;
    UUID player;
    URI server;
    UUID connection;

    @Override
    public String getRequestShardKey() {
        return user.toString();
    }
}
