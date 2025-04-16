package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.GetServiceTokenRequest;
import com.omgservers.service.server.state.dto.GetServiceTokenResponse;
import com.omgservers.service.server.state.impl.operation.ChangeStateOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetServiceTokenMethodImpl implements GetServiceTokenMethod {

    final ChangeStateOperation changeStateOperation;

    @Override
    public GetServiceTokenResponse execute(final GetServiceTokenRequest request) {
        final var serviceToken = changeStateOperation.getServiceToken();
        return new GetServiceTokenResponse(serviceToken);
    }
}
