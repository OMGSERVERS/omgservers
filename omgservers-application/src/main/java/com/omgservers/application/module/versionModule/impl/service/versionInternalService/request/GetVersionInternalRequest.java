package com.omgservers.application.module.versionModule.impl.service.versionInternalService.request;

import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionInternalRequest implements InternalRequest {

    static public void validate(GetVersionInternalRequest request) {
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
