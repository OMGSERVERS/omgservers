package com.omgservers.model.dto.client;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.message.MessageModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleMessageRequest implements ShardedRequest {

    @NotNull
    Long fromUserId;

    @NotNull
    Long clientId;

    @NotNull
    MessageModel message;

    @Override
    public String getRequestShardKey() {
        return clientId.toString();
    }
}
