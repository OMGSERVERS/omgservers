package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request;

import com.omgservers.application.module.tenantModule.model.project.ProjectPermissionEnum;
import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HasProjectPermissionInternalRequest implements InternalRequest {

    static public void validate(HasProjectPermissionInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UUID tenant;
    UUID project;
    UUID user;
    ProjectPermissionEnum permission;

    @Override
    public String getRequestShardKey() {
        return tenant.toString();
    }
}
