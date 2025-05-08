package com.omgservers.service.shard.root.impl.operation.getRootModuleClient;

import com.omgservers.service.operation.server.GetRestClientOperationImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetRootModuleClientOperationImpl extends GetRestClientOperationImpl<RootModuleClient>
        implements GetRootModuleClientOperation {

    public GetRootModuleClientOperationImpl() {
        super(RootModuleClient.class);
    }
}
