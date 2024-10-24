package com.omgservers.schema.module.pool.poolState;

import com.omgservers.schema.model.poolChangeOfState.PoolChangeOfStateDto;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePoolStateRequest implements ShardedRequest {

    @NotNull
    Long poolId;

    @NotNull
    PoolChangeOfStateDto poolChangeOfState;

    @Override
    public String getRequestShardKey() {
        return poolId.toString();
    }
}
