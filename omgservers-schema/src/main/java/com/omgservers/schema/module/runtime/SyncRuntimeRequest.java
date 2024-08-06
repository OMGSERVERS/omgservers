package com.omgservers.schema.module.runtime;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.module.ShardedRequest;
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
