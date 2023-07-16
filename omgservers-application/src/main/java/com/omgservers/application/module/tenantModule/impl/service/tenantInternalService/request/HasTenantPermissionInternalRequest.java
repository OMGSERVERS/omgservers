package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request;

import com.omgservers.application.module.tenantModule.model.tenant.TenantPermissionEnum;
import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HasTenantPermissionInternalRequest implements InternalRequest {

    static public void validate(HasTenantPermissionInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UUID tenant;
    UUID user;
    TenantPermissionEnum permission;

    @Override
    public String getRequestShardKey() {
        return tenant.toString();
    }
}
