package com.omgservers.dto.runtime;

import com.omgservers.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindRuntimeGrantRequest implements ShardedRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    Long entityId;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
