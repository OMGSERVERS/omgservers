package com.omgservers.schema.shard.client.clientMessage;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewClientMessagesRequest implements ShardRequest {

    @NotNull
    Long clientId;

    @Override
    public String getRequestShardKey() {
        return clientId.toString();
    }
}
