package com.omgservers.service.shard.pool.impl.operation.getPoolModuleClient;

import com.omgservers.service.operation.server.GetRestClientOperationImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetPoolModuleClientOperationImpl extends GetRestClientOperationImpl<PoolModuleClient>
        implements GetPoolModuleClientOperation {

    public GetPoolModuleClientOperationImpl() {
        super(PoolModuleClient.class);
    }
}
