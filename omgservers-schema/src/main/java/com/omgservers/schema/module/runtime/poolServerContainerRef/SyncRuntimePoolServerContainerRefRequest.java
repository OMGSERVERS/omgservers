package com.omgservers.schema.module.runtime.poolServerContainerRef;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.runtimePoolServerContainerRef.RuntimePoolServerContainerRefModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRuntimePoolServerContainerRefRequest implements ShardedRequest {

    @NotNull
    RuntimePoolServerContainerRefModel runtimePoolServerContainerRef;

    @Override
    public String getRequestShardKey() {
        return runtimePoolServerContainerRef.getRuntimeId().toString();
    }
}
