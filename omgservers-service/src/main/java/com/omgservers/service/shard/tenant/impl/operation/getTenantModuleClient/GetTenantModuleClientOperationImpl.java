package com.omgservers.service.shard.tenant.impl.operation.getTenantModuleClient;

import com.omgservers.service.operation.server.GetRestClientOperationImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetTenantModuleClientOperationImpl extends GetRestClientOperationImpl<TenantModuleClient>
        implements GetTenantModuleClientOperation {

    public GetTenantModuleClientOperationImpl() {
        super(TenantModuleClient.class);
    }
}
