package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.schema.master.node.AcquireNodeRequest;
import com.omgservers.schema.master.node.AcquireNodeResponse;
import com.omgservers.schema.model.node.NodeModel;
import com.omgservers.service.master.node.NodeMaster;
import com.omgservers.service.server.state.StateService;
import com.omgservers.service.server.state.dto.SetNodeIdRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AcquireNodeIdMethodImpl implements AcquireNodeIdMethod {

    final NodeMaster nodeMaster;

    final StateService stateService;

    @Override
    public Uni<Void> execute() {
        log.debug("Acquire nodeId");

        return acquireNode()
                .invoke(node -> {
                    final var nodeId = node.getId();
                    setNodeId(nodeId);

                    log.info("NodeId \"{}\" acquired", nodeId);
                })
                .replaceWithVoid();
    }

    Uni<NodeModel> acquireNode() {
        final var request = new AcquireNodeRequest();
        return nodeMaster.getService().execute(request)
                .map(AcquireNodeResponse::getNode);
    }

    void setNodeId(final Long nodeId) {
        final var request = new SetNodeIdRequest(nodeId);
        stateService.execute(request);
    }
}
