package com.omgservers.schema.module.runtime;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRuntimeCommandRequest implements ShardedRequest {

    @NotNull
    RuntimeCommandModel runtimeCommand;

    @Override
    public String getRequestShardKey() {
        return runtimeCommand.getRuntimeId().toString();
    }
}
