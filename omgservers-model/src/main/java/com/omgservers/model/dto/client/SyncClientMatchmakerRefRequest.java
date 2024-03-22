package com.omgservers.model.dto.client;

import com.omgservers.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncClientMatchmakerRefRequest implements ShardedRequest {

    @NotNull
    ClientMatchmakerRefModel clientMatchmakerRef;

    @Override
    public String getRequestShardKey() {
        return clientMatchmakerRef.getClientId().toString();
    }
}
