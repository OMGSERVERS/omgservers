package com.omgservers.service.master.node.impl.operation;

import com.omgservers.service.operation.server.GetRestClientOperationImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetNodeMasterClientOperationImpl extends GetRestClientOperationImpl<NodeMasterClient>
        implements GetNodeMasterClientOperation {

    public GetNodeMasterClientOperationImpl() {
        super(NodeMasterClient.class);
    }
}
