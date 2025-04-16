package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.GetNodeIdRequest;
import com.omgservers.service.server.state.dto.GetNodeIdResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetNodeIdMethodImpl implements GetNodeIdMethod {

    final StateServiceState stateServiceState;

    @Override
    public GetNodeIdResponse execute(final GetNodeIdRequest request) {
        final var nodeId = stateServiceState.getNodeId();
        return new GetNodeIdResponse(nodeId);
    }
}
