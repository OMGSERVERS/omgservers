package com.omgservers.service.shard.runtime.impl.operation.getRuntimeModuleClient;

import com.omgservers.service.operation.server.GetRestClientOperationImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetRuntimeModuleClientOperationImpl extends GetRestClientOperationImpl<RuntimeModuleClient>
        implements GetRuntimeModuleClientOperation {

    public GetRuntimeModuleClientOperationImpl() {
        super(RuntimeModuleClient.class);
    }
}
