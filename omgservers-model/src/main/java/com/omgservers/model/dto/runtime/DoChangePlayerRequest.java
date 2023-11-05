package com.omgservers.model.dto.runtime;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoChangePlayerRequest implements ShardedRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    Long userId;

    @NotNull
    Long clientId;

    @NotNull
    Object message;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
