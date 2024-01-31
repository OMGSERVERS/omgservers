package com.omgservers.model.dto.client;

import com.omgservers.model.clientRuntime.ClientRuntimeModel;
import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncClientRuntimeRequest implements ShardedRequest {

    @NotNull
    ClientRuntimeModel clientRuntime;

    @Override
    public String getRequestShardKey() {
        return clientRuntime.getClientId().toString();
    }
}
