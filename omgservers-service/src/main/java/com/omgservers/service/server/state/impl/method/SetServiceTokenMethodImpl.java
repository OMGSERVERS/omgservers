package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.SetServiceTokenRequest;
import com.omgservers.service.server.state.dto.SetServiceTokenResponse;
import com.omgservers.service.server.state.impl.operation.ExecuteStateOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SetServiceTokenMethodImpl implements SetServiceTokenMethod {

    final ExecuteStateOperation executeStateOperation;

    @Override
    public SetServiceTokenResponse execute(final SetServiceTokenRequest request) {
        final var serviceToken = request.getServiceToken();
        executeStateOperation.setServerToken(serviceToken);

        log.info("Service token set");

        return new SetServiceTokenResponse();
    }
}
