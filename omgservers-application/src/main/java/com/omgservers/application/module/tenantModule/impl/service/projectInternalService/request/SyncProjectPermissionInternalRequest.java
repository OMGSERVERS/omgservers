package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request;

import com.omgservers.application.module.tenantModule.model.project.ProjectPermissionModel;
import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncProjectPermissionInternalRequest implements InternalRequest {

    static public void validate(SyncProjectPermissionInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UUID tenant;
    ProjectPermissionModel permission;

    @Override
    public String getRequestShardKey() {
        return tenant.toString();
    }
}
