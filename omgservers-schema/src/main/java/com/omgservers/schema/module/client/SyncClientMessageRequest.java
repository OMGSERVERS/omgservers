package com.omgservers.schema.module.client;

import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncClientMessageRequest implements ShardedRequest {

    @NotNull
    ClientMessageModel clientMessage;

    @Override
    public String getRequestShardKey() {
        return clientMessage.getClientId().toString();
    }
}
