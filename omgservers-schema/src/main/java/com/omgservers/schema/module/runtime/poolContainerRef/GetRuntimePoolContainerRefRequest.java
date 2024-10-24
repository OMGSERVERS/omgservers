package com.omgservers.schema.module.runtime.poolContainerRef;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRuntimePoolContainerRefRequest implements ShardedRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
