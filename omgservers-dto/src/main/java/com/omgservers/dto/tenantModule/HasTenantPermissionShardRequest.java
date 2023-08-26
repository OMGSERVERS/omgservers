package com.omgservers.dto.tenantModule;

import com.omgservers.dto.ShardRequest;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HasTenantPermissionShardRequest implements ShardRequest {

    static public void validate(HasTenantPermissionShardRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long tenantId;
    Long userId;
    TenantPermissionEnum permission;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
