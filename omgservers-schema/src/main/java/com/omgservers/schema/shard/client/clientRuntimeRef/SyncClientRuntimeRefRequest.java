package com.omgservers.schema.shard.client.clientRuntimeRef;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.schema.shard.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncClientRuntimeRefRequest implements ShardedRequest {

    @NotNull
    ClientRuntimeRefModel clientRuntimeRef;

    @Override
    public String getRequestShardKey() {
        return clientRuntimeRef.getClientId().toString();
    }
}
