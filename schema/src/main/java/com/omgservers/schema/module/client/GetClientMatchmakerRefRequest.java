package com.omgservers.schema.module.client;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetClientMatchmakerRefRequest implements ShardedRequest {

    @NotNull
    Long clientId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return clientId.toString();
    }
}
