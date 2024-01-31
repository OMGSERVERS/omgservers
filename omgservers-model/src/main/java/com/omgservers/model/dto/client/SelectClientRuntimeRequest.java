package com.omgservers.model.dto.client;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectClientRuntimeRequest implements ShardedRequest {

    @NotNull
    Long clientId;

    @NotNull
    Strategy strategy;

    @Override
    public String getRequestShardKey() {
        return clientId.toString();
    }

    public enum Strategy {
        LATEST,
    }
}
