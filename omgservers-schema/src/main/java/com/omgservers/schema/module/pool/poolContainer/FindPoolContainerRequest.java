package com.omgservers.schema.module.pool.poolContainer;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPoolContainerRequest implements ShardedRequest {

    @NotNull
    Long poolId;

    @NotNull
    Long runtimeId;

    @Override
    public String getRequestShardKey() {
        return poolId.toString();
    }
}
