package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.GetX5CRequest;
import com.omgservers.service.server.state.dto.GetX5CResponse;
import com.omgservers.service.server.state.impl.operation.ExecuteStateOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetX5CMethodImpl implements GetX5CMethod {

    final ExecuteStateOperation executeStateOperation;

    @Override
    public GetX5CResponse execute(final GetX5CRequest request) {
        final var x5c = executeStateOperation.getX5C();
        return new GetX5CResponse(x5c);
    }
}
