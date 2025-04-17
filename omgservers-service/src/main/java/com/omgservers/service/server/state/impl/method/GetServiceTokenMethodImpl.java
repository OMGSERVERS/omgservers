package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.GetServiceTokenRequest;
import com.omgservers.service.server.state.dto.GetServiceTokenResponse;
import com.omgservers.service.server.state.impl.operation.ExecuteStateOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetServiceTokenMethodImpl implements GetServiceTokenMethod {

    final ExecuteStateOperation executeStateOperation;

    @Override
    public GetServiceTokenResponse execute(final GetServiceTokenRequest request) {
        final var serviceToken = executeStateOperation.getServerToken();
        return new GetServiceTokenResponse(serviceToken);
    }
}
