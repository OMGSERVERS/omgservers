package com.omgservers.schema.module.tenant.tenantImageRef;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.tenantImageRef.TenantImageRefQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindTenantImageRefRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long versionId;

    @NotNull
    TenantImageRefQualifierEnum tenantImageRefQualifier;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
