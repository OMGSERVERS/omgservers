package com.omgservers.application.module.userModule.impl.service.attributeInternalService.request;

import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAttributeInternalRequest implements InternalRequest {

    static public void validate(DeleteAttributeInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UUID user;
    UUID player;
    String name;

    @Override
    public String getRequestShardKey() {
        return user.toString();
    }
}
