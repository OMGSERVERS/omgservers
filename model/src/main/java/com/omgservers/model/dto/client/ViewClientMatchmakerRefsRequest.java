package com.omgservers.model.dto.client;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewClientMatchmakerRefsRequest implements ShardedRequest {

    @NotNull
    Long clientId;

    @Override
    public String getRequestShardKey() {
        return clientId.toString();
    }
}
