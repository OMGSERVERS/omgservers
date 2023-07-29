package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request;

import com.omgservers.application.module.runtimeModule.model.RuntimeModel;
import com.omgservers.application.request.InternalRequest;
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