package com.omgservers.schema.module.client;

import com.omgservers.schema.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncClientMatchmakerRefRequest implements ShardedRequest {

    @NotNull
    ClientMatchmakerRefModel clientMatchmakerRef;

    @Override
    public String getRequestShardKey() {
        return clientMatchmakerRef.getClientId().toString();
    }
}
