package com.omgservers.schema.module.runtime.runtimeMessage;

import com.omgservers.schema.message.MessageModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterchangeMessagesRequest implements ShardedRequest {

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
