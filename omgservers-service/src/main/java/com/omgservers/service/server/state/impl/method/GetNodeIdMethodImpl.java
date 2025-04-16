package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.GetNodeIdRequest;
import com.omgservers.service.server.state.dto.GetNodeIdResponse;
import com.omgservers.service.server.state.impl.operation.ExecuteStateOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetNodeIdMethodImpl implements GetNodeIdMethod {

    final ExecuteStateOperation executeStateOperation;

    @Override
    public GetNodeIdResponse execute(final GetNodeIdRequest request) {
        final var nodeId = executeStateOperation.getNodeId();
        return new GetNodeIdResponse(nodeId);
    }
}
