package com.omgservers.schema.shard.pool.poolRequest;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPoolRequestRequest implements ShardRequest {

    @NotNull
    Long poolId;

    @NotNull
    Long runtimeId;

    @Override
    public String getRequestShardKey() {
        return poolId.toString();
    }
}
