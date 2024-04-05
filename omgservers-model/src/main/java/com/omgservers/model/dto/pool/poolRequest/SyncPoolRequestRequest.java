package com.omgservers.model.dto.pool.poolRequest;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.poolRequest.PoolRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPoolRequestRequest implements ShardedRequest {

    @NotNull
    PoolRequestModel poolRequest;

    @Override
    public String getRequestShardKey() {
        return poolRequest.getPoolId().toString();
    }
}
