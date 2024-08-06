package com.omgservers.schema.module.client;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.message.MessageModel;
import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.message.MessageModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterchangeRequest implements ShardedRequest {

    @NotNull
    Long fromUserId;

    @NotNull
    Long clientId;

    @NotNull
    List<MessageModel> outgoingMessages;

    @NotNull
    List<Long> consumedMessages;

    @Override
    public String getRequestShardKey() {
        return clientId.toString();
    }
}
