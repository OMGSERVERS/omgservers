package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request;

import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteStageInternalRequest implements InternalRequest {

    static public void validate(DeleteStageInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    UUID tenant;
    UUID uuid;

    @Override
    public String getRequestShardKey() {
        return tenant.toString();
    }
}
