package com.omgservers.dto.tenant;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.tenant.TenantModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantRequest implements ShardedRequest {

    public static void validate(SyncTenantRequest request) {
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