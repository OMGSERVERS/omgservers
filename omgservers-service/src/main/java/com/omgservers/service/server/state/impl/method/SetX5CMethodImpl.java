package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.SetX5CRequest;
import com.omgservers.service.server.state.dto.SetX5CResponse;
import com.omgservers.service.server.state.impl.operation.ExecuteStateOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SetX5CMethodImpl implements SetX5CMethod {

    final ExecuteStateOperation executeStateOperation;

    @Override
    public SetX5CResponse execute(final SetX5CRequest request) {
        final var x5c = request.getX5c();
        executeStateOperation.setX5C(x5c);

        log.info("X5C set, {}", x5c);

        return new SetX5CResponse();
    }
}
