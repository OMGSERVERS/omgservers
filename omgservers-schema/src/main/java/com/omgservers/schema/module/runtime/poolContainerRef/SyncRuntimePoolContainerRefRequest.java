package com.omgservers.schema.module.runtime.poolContainerRef;

import com.omgservers.schema.model.runtimePoolContainerRef.RuntimePoolContainerRefModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRuntimePoolContainerRefRequest implements ShardedRequest {

    @NotNull
    RuntimePoolContainerRefModel runtimePoolContainerRef;

    @Override
    public String getRequestShardKey() {
        return runtimePoolContainerRef.getRuntimeId().toString();
    }
}
