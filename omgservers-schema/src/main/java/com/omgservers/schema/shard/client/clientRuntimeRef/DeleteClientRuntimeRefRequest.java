package com.omgservers.schema.shard.client.clientRuntimeRef;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteClientRuntimeRefRequest implements ShardRequest {

    @NotNull
    Long clientId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return clientId.toString();
    }
}
