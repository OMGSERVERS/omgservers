package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.GetNodeIdRequest;
import com.omgservers.service.server.state.dto.GetNodeIdResponse;
import com.omgservers.service.server.state.impl.operation.ChangeStateOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetNodeIdMethodImpl implements GetNodeIdMethod {

    final ChangeStateOperation changeStateOperation;

    @Override
    public GetNodeIdResponse execute(final GetNodeIdRequest request) {
        final var nodeId = changeStateOperation.getNodeId();
        return new GetNodeIdResponse(nodeId);
    }
}
