package com.omgservers.schema.shard.docker;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PingDockerHostRequest implements ShardRequest {

    @NotNull
    PoolServerModel poolServer;

    @Override
    public String getRequestShardKey() {
        return poolServer.getId().toString();
    }
}
