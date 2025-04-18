package com.omgservers.schema.shard.tenant.tenantProject;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTenantProjectRequest implements ShardRequest {

    @Valid
    Long tenantId;

    @Valid
    Long id;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
