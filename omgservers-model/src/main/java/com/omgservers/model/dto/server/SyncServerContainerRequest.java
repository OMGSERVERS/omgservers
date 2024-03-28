package com.omgservers.model.dto.server;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.serverContainer.ServerContainerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncServerContainerRequest implements ShardedRequest {

    @NotNull
    ServerContainerModel serverContainer;

    @Override
    public String getRequestShardKey() {
        return serverContainer.getServerId().toString();
    }
}
