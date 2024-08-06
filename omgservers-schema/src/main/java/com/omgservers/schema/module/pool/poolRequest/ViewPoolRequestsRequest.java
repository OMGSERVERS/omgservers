package com.omgservers.schema.module.pool.poolRequest;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoolRequestsRequest implements ShardedRequest {

    @NotNull
    Long poolId;

    @Override
    public String getRequestShardKey() {
        return poolId.toString();
    }
}
