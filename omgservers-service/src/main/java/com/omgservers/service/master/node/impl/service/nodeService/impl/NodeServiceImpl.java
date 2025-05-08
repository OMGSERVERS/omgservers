package com.omgservers.service.master.node.impl.service.nodeService.impl;

import com.omgservers.schema.master.node.AcquireNodeRequest;
import com.omgservers.schema.master.node.AcquireNodeResponse;
import com.omgservers.service.master.node.impl.operation.GetNodeMasterClientOperation;
import com.omgservers.service.master.node.impl.service.nodeService.NodeService;
import com.omgservers.service.master.node.impl.service.nodeService.impl.method.AcquireNodeMethod;
import com.omgservers.service.master.node.impl.service.webService.impl.api.NodeApi;
import com.omgservers.service.operation.server.HandleMasterRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class NodeServiceImpl implements NodeService {

    final AcquireNodeMethod acquireNodeMethod;

    final HandleMasterRequestOperation handleMasterRequestOperation;
    final GetNodeMasterClientOperation getNodeMasterClientOperation;

    @Override
    public Uni<AcquireNodeResponse> execute(@Valid final AcquireNodeRequest request) {
        return handleMasterRequestOperation.execute(log, request,
                getNodeMasterClientOperation::execute,
                NodeApi::execute,
                acquireNodeMethod::execute);
    }
}
