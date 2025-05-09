package com.omgservers.schema.shard.client.clientMessage;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteClientMessagesRequest implements ShardRequest {

    @NotNull
    Long clientId;

    @NotEmpty
    List<Long> ids;

    @Override
    public String getRequestShardKey() {
        return clientId.toString();
    }
}
