package com.omgservers.model.dto.pool.poolServerContainer;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoolServerContainersRequest implements ShardedRequest {

    @NotNull
    Long poolId;

    Long serverId;

    @Override
    public String getRequestShardKey() {
        return poolId.toString();
    }
}
