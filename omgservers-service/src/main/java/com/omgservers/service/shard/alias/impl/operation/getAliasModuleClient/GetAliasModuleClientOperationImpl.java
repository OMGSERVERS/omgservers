package com.omgservers.service.shard.alias.impl.operation.getAliasModuleClient;

import com.omgservers.service.operation.server.GetRestClientOperationImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetAliasModuleClientOperationImpl extends GetRestClientOperationImpl<AliasModuleClient>
        implements GetAliasModuleClientOperation {

    public GetAliasModuleClientOperationImpl() {
        super(AliasModuleClient.class);
    }
}
