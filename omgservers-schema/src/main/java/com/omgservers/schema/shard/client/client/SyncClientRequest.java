package com.omgservers.schema.shard.client.client;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncClientRequest implements ShardRequest {

    @NotNull
    ClientModel client;

    @Override
    public String getRequestShardKey() {
        return client.getId().toString();
    }
}
