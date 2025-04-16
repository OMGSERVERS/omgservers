package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.SetServiceTokenRequest;
import com.omgservers.service.server.state.dto.SetServiceTokenResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SetServiceTokenMethodImpl implements SetServiceTokenMethod {

    final StateServiceState stateServiceState;

    @Override
    public SetServiceTokenResponse execute(final SetServiceTokenRequest request) {
        final var serviceToken = request.getServiceToken();
        stateServiceState.setServiceToken(serviceToken);

        log.info("Service token is set");

        return new SetServiceTokenResponse();
    }
}
