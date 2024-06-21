package com.omgservers.model.dto.client;

import com.omgservers.model.clientMessage.ClientMessageModel;
import com.omgservers.model.dto.ShardedRequest;
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
