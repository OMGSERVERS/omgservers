package com.omgservers.schema.module.pool.poolCommand;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPoolCommandRequest implements ShardedRequest {

    @NotNull
    Long poolId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return poolId.toString();
    }
}
