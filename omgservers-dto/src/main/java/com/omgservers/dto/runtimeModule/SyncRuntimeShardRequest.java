package com.omgservers.dto.runtimeModule;

import com.omgservers.dto.ShardRequest;
import com.omgservers.model.runtime.RuntimeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRuntimeShardRequest implements ShardRequest {

    static public void validate(SyncRuntimeShardRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    RuntimeModel runtime;

    @Override
    public String getRequestShardKey() {
        return runtime.getId().toString();
    }
}
