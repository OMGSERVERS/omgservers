package com.omgservers.schema.shard.runtime.runtimeMessage;

import com.omgservers.schema.message.MessageModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterchangeMessagesRequest implements ShardRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    List<MessageModel> outgoingMessages;

    @NotNull
    List<Long> consumedMessages;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
