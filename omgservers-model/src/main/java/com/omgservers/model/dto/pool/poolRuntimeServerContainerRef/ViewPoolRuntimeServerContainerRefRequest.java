package com.omgservers.model.dto.pool.poolRuntimeServerContainerRef;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoolRuntimeServerContainerRefRequest implements ShardedRequest {

    @NotNull
    Long poolId;

    @Override
    public String getRequestShardKey() {
        return poolId.toString();
    }
}
