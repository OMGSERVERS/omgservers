package com.omgservers.schema.module.tenant.versionImageRef;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteVersionImageRefRequest implements ShardedRequest {

    @Valid
    Long tenantId;

    @Valid
    Long id;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}