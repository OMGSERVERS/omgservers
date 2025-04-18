package com.omgservers.schema.shard.pool.poolRequest;

import com.omgservers.schema.shard.ShardRequest;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPoolRequestRequest implements ShardRequest {

    @NotNull
    PoolRequestModel poolRequest;

    @Override
    public String getRequestShardKey() {
        return poolRequest.getPoolId().toString();
    }
}
