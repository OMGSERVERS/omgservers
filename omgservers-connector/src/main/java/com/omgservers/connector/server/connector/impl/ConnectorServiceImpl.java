package com.omgservers.connector.server.connector.impl;

import com.omgservers.connector.server.connector.ConnectorService;
import com.omgservers.connector.server.connector.dto.CreateConnectorRequest;
import com.omgservers.connector.server.connector.dto.CreateConnectorResponse;
import com.omgservers.connector.server.connector.dto.DeleteConnectorRequest;
import com.omgservers.connector.server.connector.dto.DeleteConnectorResponse;
import com.omgservers.connector.server.connector.dto.InterchangeMessagesRequest;
import com.omgservers.connector.server.connector.dto.InterchangeMessagesResponse;
import com.omgservers.connector.server.connector.dto.ReceiveTextMessageRequest;
import com.omgservers.connector.server.connector.dto.ReceiveTextMessageResponse;
import com.omgservers.connector.server.connector.impl.method.CreateConnectorMethod;
import com.omgservers.connector.server.connector.impl.method.DeleteConnectorMethod;
import com.omgservers.connector.server.connector.impl.method.InterchangeMessagesMethod;
import com.omgservers.connector.server.connector.impl.method.ReceiveTextMessageMethod;
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

    final ReceiveTextMessageMethod receiveTextMessageMethod;
    final InterchangeMessagesMethod interchangeMessagesMethod;
    final CreateConnectorMethod createConnectorMethod;
    final DeleteConnectorMethod deleteConnectorMethod;

    @Override
    public Uni<CreateConnectorResponse> execute(@Valid final CreateConnectorRequest request) {
        return createConnectorMethod.execute(request);
    }

    @Override
    public Uni<DeleteConnectorResponse> execute(@Valid final DeleteConnectorRequest request) {
        return deleteConnectorMethod.execute(request);
    }

    @Override
    public Uni<ReceiveTextMessageResponse> execute(@Valid final ReceiveTextMessageRequest request) {
        return receiveTextMessageMethod.execute(request);
    }

    @Override
    public Uni<InterchangeMessagesResponse> execute(@Valid final InterchangeMessagesRequest request) {
        return interchangeMessagesMethod.execute(request);
    }
}
