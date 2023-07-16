package com.omgservers.application.module.userModule.impl.service.clientInternalService.request;

import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteClientInternalRequest implements InternalRequest {

    static public void validate(DeleteClientInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    UUID user;
    UUID client;

    @Override
    public String getRequestShardKey() {
        return user.toString();
    }
}
