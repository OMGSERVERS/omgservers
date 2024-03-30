package com.omgservers.model.dto.runtime.serverRuntimeRef;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.runtimeServerContainerRef.RuntimeServerContainerRefModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRuntimeServerContainerRefRequest implements ShardedRequest {

    @NotNull
    RuntimeServerContainerRefModel runtimeServerContainerRef;

    @Override
    public String getRequestShardKey() {
        return runtimeServerContainerRef.getRuntimeId().toString();
    }
}
