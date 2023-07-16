package com.omgservers.application.module.userModule.impl.service.objectInternalService.request;

import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteObjectInternalRequest implements InternalRequest {

    static public void validate(DeleteObjectInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UUID user;
    UUID uuid;

    @Override
    public String getRequestShardKey() {
        return user.toString();
    }
}
