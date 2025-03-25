package com.omgservers.schema.module.pool.poolCommand;

import com.omgservers.schema.model.poolCommand.PoolCommandModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPoolCommandRequest implements ShardedRequest {

    @NotNull
    PoolCommandModel poolCommand;

    @Override
    public String getRequestShardKey() {
        return poolCommand.getPoolId().toString();
    }
}
