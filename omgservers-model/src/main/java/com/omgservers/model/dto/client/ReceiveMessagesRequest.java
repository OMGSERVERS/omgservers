package com.omgservers.model.dto.client;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveMessagesRequest implements ShardedRequest {

    @NotNull
    Long forUserId;

    @NotNull
    Long clientId;

    @NotNull
    List<Long> consumedMessages;

    @Override
    public String getRequestShardKey() {
        return clientId.toString();
    }
}
