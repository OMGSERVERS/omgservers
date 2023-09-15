package com.omgservers.dto.user;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.client.ClientModel;
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
        return client.getUserId().toString();
    }
}
