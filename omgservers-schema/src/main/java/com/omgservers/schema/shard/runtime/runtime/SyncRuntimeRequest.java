package com.omgservers.schema.shard.runtime.runtime;

import com.omgservers.schema.shard.ShardedRequest;
import com.omgservers.schema.model.runtime.RuntimeModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRuntimeRequest implements ShardedRequest {

    @NotNull
    RuntimeModel runtime;

    @Override
    public String getRequestShardKey() {
        return runtime.getId().toString();
    }
}
