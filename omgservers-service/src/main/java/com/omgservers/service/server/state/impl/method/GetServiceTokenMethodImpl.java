package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.GetServiceTokenRequest;
import com.omgservers.service.server.state.dto.GetServiceTokenResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetServiceTokenMethodImpl implements GetServiceTokenMethod {

    final StateServiceState stateServiceState;

    @Override
    public GetServiceTokenResponse execute(final GetServiceTokenRequest request) {
        final var serviceToken = stateServiceState.getServiceToken();
        return new GetServiceTokenResponse(serviceToken);
    }
}
