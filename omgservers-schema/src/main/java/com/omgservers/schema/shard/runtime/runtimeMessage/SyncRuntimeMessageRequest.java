package com.omgservers.schema.shard.runtime.runtimeMessage;

import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import com.omgservers.schema.shard.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRuntimeMessageRequest implements ShardedRequest {

    @NotNull
    RuntimeMessageModel runtimeMessage;

    @Override
    public String getRequestShardKey() {
        return runtimeMessage.getRuntimeId().toString();
    }
}
