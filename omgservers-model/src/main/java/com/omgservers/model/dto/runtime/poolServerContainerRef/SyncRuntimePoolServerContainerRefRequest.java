package com.omgservers.model.dto.runtime.poolServerContainerRef;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.runtimePoolServerContainerRef.RuntimePoolServerContainerRefModel;
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
