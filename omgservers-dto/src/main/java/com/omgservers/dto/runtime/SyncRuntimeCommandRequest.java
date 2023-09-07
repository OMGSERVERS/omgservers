package com.omgservers.dto.runtime;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRuntimeCommandRequest implements ShardedRequest {

    public static void validate(SyncRuntimeCommandRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    RuntimeCommandModel runtimeCommand;

    @Override
    public String getRequestShardKey() {
        return runtimeCommand.getRuntimeId().toString();
    }
}
