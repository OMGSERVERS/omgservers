package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.schema.master.node.AcquireNodeRequest;
import com.omgservers.schema.master.node.AcquireNodeResponse;
import com.omgservers.schema.model.node.NodeModel;
import com.omgservers.service.master.node.NodeMaster;
import com.omgservers.service.operation.server.ExecuteStateOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AcquireNodeIdMethodImpl implements AcquireNodeIdMethod {

    final NodeMaster nodeMaster;

    final ExecuteStateOperation executeStateOperation;

    @Override
    public Uni<Void> execute() {
        log.info("Acquire nodeId");

        return acquireNode()
                .invoke(node -> {
                    final var nodeId = node.getId();
                    executeStateOperation.setNodeId(nodeId);

                    log.info("NodeId \"{}\" acquired", nodeId);
                })
                .replaceWithVoid();
    }

    Uni<NodeModel> acquireNode() {
        final var request = new AcquireNodeRequest();
        return nodeMaster.getService().execute(request)
                .map(AcquireNodeResponse::getNode);
    }
}
