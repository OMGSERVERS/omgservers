package com.omgservers.model.dto.server;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewServerContainersRequest implements ShardedRequest {

    @NotNull
    Long serverId;

    @Override
    public String getRequestShardKey() {
        return serverId.toString();
    }
}
