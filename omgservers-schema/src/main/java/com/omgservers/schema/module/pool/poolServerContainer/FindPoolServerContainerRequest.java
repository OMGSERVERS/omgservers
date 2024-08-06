package com.omgservers.schema.module.pool.poolServerContainer;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPoolServerContainerRequest implements ShardedRequest {

    @NotNull
    Long poolId;

    @NotNull
    Long serverId;

    @NotNull
    Long runtimeId;

    @Override
    public String getRequestShardKey() {
        return poolId.toString();
    }
}
