package com.omgservers.schema.shard.tenant.tenantImage;

import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindTenantImageRequest implements ShardRequest {

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
