package com.omgservers.schema.module.tenant.tenantDeployment;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTenantDeploymentRequest implements ShardedRequest {

    @Valid
    Long tenantId;

    @Valid
    Long id;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
