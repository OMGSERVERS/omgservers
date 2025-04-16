package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.SetNodeIdRequest;
import com.omgservers.service.server.state.dto.SetNodeIdResponse;
import com.omgservers.service.server.state.impl.operation.ChangeStateOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SetNodeIdMethodImpl implements SetNodeIdMethod {

    final ChangeStateOperation changeStateOperation;

    @Override
    public SetNodeIdResponse execute(final SetNodeIdRequest request) {
        final var nodeId = request.getNodeId();
        changeStateOperation.setNodeId(nodeId);

        log.info("Node \"{}\" is set", nodeId);

        return new SetNodeIdResponse();
    }
}
