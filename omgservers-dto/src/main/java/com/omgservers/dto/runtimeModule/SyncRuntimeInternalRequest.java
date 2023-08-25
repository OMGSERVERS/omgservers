package com.omgservers.dto.runtimeModule;

import com.omgservers.dto.InternalRequest;
import com.omgservers.model.runtime.RuntimeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRuntimeInternalRequest implements InternalRequest {

    static public void validate(SyncRuntimeInternalRequest request) {
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
