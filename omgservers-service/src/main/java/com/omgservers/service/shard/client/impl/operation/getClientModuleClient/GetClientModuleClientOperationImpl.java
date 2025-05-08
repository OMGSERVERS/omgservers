package com.omgservers.service.shard.client.impl.operation.getClientModuleClient;

import com.omgservers.service.operation.server.GetRestClientOperationImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetClientModuleClientOperationImpl extends GetRestClientOperationImpl<ClientModuleClient>
        implements GetClientModuleClientOperation {

    public GetClientModuleClientOperationImpl() {
        super(ClientModuleClient.class);
    }
}
