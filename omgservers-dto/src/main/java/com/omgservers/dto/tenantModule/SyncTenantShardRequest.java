package com.omgservers.dto.tenantModule;

import com.omgservers.dto.ShardRequest;
import com.omgservers.model.tenant.TenantModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantShardRequest implements ShardRequest {

    static public void validate(SyncTenantShardRequest request) {
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
