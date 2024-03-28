package com.omgservers.model.dto.server;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.server.ServerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncServerRequest implements ShardedRequest {

    @NotNull
    ServerModel server;

    @Override
    public String getRequestShardKey() {
        return server.getId().toString();
    }
}
