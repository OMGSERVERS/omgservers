package com.omgservers.service.entrypoint.connector.impl.service.connectorService.impl;

import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorRequest;
import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorResponse;
import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorRequest;
import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorResponse;
import com.omgservers.service.entrypoint.connector.impl.service.connectorService.ConnectorService;
import com.omgservers.service.entrypoint.connector.impl.service.connectorService.impl.method.CreateTokenMethod;
import com.omgservers.service.entrypoint.connector.impl.service.connectorService.impl.method.InterchangeMessagesMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ConnectorServiceImpl implements ConnectorService {

    final InterchangeMessagesMethod interchangeMessagesMethod;
    final CreateTokenMethod createTokenMethod;

    @Override
    public Uni<CreateTokenConnectorResponse> execute(@Valid final CreateTokenConnectorRequest request) {
        return createTokenMethod.execute(request);
    }

    @Override
    public Uni<InterchangeMessagesConnectorResponse> execute(@Valid final InterchangeMessagesConnectorRequest request) {
        return interchangeMessagesMethod.execute(request);
    }
}
