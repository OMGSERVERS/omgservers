package com.omgservers.model.dto.runtime;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
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
