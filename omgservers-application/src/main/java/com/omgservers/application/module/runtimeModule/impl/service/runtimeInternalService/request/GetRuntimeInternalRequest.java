package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request;

import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRuntimeInternalRequest implements InternalRequest {

    static public void validate(GetRuntimeInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UUID uuid;

    @Override
    public String getRequestShardKey() {
        return uuid.toString();
    }
}
