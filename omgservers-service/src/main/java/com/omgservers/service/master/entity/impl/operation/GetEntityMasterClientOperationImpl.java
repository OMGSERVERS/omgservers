package com.omgservers.service.master.entity.impl.operation;

import com.omgservers.service.operation.server.GetRestClientOperationImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetEntityMasterClientOperationImpl extends GetRestClientOperationImpl<EntityMasterClient>
        implements GetEntityMasterClientOperation {

    public GetEntityMasterClientOperationImpl() {
        super(EntityMasterClient.class);
    }
}
