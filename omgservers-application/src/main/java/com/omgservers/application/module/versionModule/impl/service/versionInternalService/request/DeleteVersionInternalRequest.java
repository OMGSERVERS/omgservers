package com.omgservers.application.module.versionModule.impl.service.versionInternalService.request;

import com.omgservers.application.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteVersionInternalRequest implements InternalRequest {

    static public void validate(DeleteVersionInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long id;

    @Override
    public String getRequestShardKey() {
        return id.toString();
    }
}
