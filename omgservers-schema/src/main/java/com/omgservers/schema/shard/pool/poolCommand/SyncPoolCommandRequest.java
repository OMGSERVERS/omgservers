package com.omgservers.schema.shard.pool.poolCommand;

import com.omgservers.schema.model.poolCommand.PoolCommandModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPoolCommandRequest implements ShardRequest {

    @NotNull
    PoolCommandModel poolCommand;

    @Override
    public String getRequestShardKey() {
        return poolCommand.getPoolId().toString();
    }
}
