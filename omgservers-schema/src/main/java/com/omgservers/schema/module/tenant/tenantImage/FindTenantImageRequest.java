package com.omgservers.schema.module.tenant.tenantImage;

import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindTenantImageRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantVersionId;

    @NotNull
    TenantImageQualifierEnum qualifier;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
