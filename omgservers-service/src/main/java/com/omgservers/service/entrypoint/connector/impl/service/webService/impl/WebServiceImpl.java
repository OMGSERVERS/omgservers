package com.omgservers.service.entrypoint.connector.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorRequest;
import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorResponse;
import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorRequest;
import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorResponse;
import com.omgservers.service.entrypoint.connector.impl.service.connectorService.ConnectorService;
import com.omgservers.service.entrypoint.connector.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final ConnectorService connectorService;

    @Override
    public Uni<CreateTokenConnectorResponse> execute(final CreateTokenConnectorRequest request) {
        return connectorService.execute(request);
    }

    @Override
    public Uni<InterchangeMessagesConnectorResponse> execute(final InterchangeMessagesConnectorRequest request) {
        return connectorService.execute(request);
    }
}
