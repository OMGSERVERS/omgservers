package com.omgservers.service.master.index.impl.operation;

import com.omgservers.service.operation.server.GetRestClientOperationImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetIndexMasterClientOperationImpl extends GetRestClientOperationImpl<IndexMasterClient>
        implements GetIndexMasterClientOperation {

    public GetIndexMasterClientOperationImpl() {
        super(IndexMasterClient.class);
    }
}
