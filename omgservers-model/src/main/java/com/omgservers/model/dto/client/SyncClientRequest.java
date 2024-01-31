package com.omgservers.model.dto.client;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncClientRequest implements ShardedRequest {

    @NotNull
    ClientModel client;

    @Override
    public String getRequestShardKey() {
        return client.getId().toString();
    }
}
