package com.omgservers.dto.tenantModule;

import com.omgservers.model.tenant.TenantModel;
import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantInternalRequest implements InternalRequest {

    static public void validate(SyncTenantInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    TenantModel tenant;

    @Override
    public String getRequestShardKey() {
        return tenant.getId().toString();
    }
}
