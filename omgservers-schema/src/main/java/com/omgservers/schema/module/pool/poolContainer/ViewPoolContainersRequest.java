package com.omgservers.schema.module.pool.poolContainer;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoolContainersRequest implements ShardedRequest {

    @NotNull
    Long poolId;

    Long serverId;

    @Override
    public String getRequestShardKey() {
        return poolId.toString();
    }
}
