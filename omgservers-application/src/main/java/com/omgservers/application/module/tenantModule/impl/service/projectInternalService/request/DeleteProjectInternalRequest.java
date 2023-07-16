package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request;

import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProjectInternalRequest implements InternalRequest {

    static public void validate(DeleteProjectInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UUID tenant;
    UUID uuid;

    @Override
    public String getRequestShardKey() {
        return tenant.toString();
    }
}
