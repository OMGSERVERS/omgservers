package com.omgservers.model.dto.client;

import com.omgservers.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncClientRuntimeRefRequest implements ShardedRequest {

    @NotNull
    ClientRuntimeRefModel clientRuntimeRef;

    @Override
    public String getRequestShardKey() {
        return clientRuntimeRef.getClientId().toString();
    }
}
