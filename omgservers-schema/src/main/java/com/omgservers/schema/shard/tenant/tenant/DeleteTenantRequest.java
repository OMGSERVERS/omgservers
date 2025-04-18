package com.omgservers.schema.shard.tenant.tenant;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTenantRequest implements ShardRequest {

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return id.toString();
    }
}
