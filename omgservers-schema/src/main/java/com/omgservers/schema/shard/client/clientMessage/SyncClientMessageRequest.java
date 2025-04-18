package com.omgservers.schema.shard.client.clientMessage;

import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncClientMessageRequest implements ShardRequest {

    @NotNull
    ClientMessageModel clientMessage;

    @Override
    public String getRequestShardKey() {
        return clientMessage.getClientId().toString();
    }
}
