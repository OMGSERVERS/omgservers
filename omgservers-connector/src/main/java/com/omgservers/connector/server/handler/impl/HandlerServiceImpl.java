package com.omgservers.connector.server.handler.impl;

import com.omgservers.connector.server.handler.HandlerService;
import com.omgservers.connector.server.handler.dto.HandleBinaryMessageRequest;
import com.omgservers.connector.server.handler.dto.HandleClosedConnectionRequest;
import com.omgservers.connector.server.handler.dto.HandleFailedConnectionRequest;
import com.omgservers.connector.server.handler.dto.HandleIdleConnectionsRequest;
import com.omgservers.connector.server.handler.dto.HandleOpenedConnectionRequest;
import com.omgservers.connector.server.handler.dto.HandleTextMessageRequest;
import com.omgservers.connector.server.handler.impl.method.HandleBinaryMessageMethod;
import com.omgservers.connector.server.handler.impl.method.HandleClosedConnectionMethod;
import com.omgservers.connector.server.handler.impl.method.HandleFailedConnectionMethod;
import com.omgservers.connector.server.handler.impl.method.HandleIdleConnectionsMethod;
import com.omgservers.connector.server.handler.impl.method.HandleOpenedConnectionMethod;
import com.omgservers.connector.server.handler.impl.method.HandleTextMessageMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandlerServiceImpl implements HandlerService {

    final HandleOpenedConnectionMethod handleOpenedConnectionMethod;
    final HandleClosedConnectionMethod handleClosedConnectionMethod;
    final HandleFailedConnectionMethod handleFailedConnectionMethod;
    final HandleIdleConnectionsMethod handleIdleConnectionsMethod;
    final HandleBinaryMessageMethod handleBinaryMessageMethod;
    final HandleTextMessageMethod handleTextMessageMethod;

    @Override
    public Uni<Void> execute(@Valid final HandleOpenedConnectionRequest request) {
        return handleOpenedConnectionMethod.execute(request);
    }

    @Override
    public Uni<Void> execute(@Valid final HandleClosedConnectionRequest request) {
        return handleClosedConnectionMethod.execute(request);
    }

    @Override
    public Uni<Void> execute(@Valid final HandleFailedConnectionRequest request) {
        return handleFailedConnectionMethod.execute(request);
    }

    @Override
    public Uni<Void> execute(@Valid final HandleTextMessageRequest request) {
        return handleTextMessageMethod.execute(request);
    }

    @Override
    public Uni<Void> execute(@Valid final HandleBinaryMessageRequest request) {
        return handleBinaryMessageMethod.execute(request);
    }

    @Override
    public Uni<Void> execute(@Valid final HandleIdleConnectionsRequest request) {
        return handleIdleConnectionsMethod.execute(request);
    }
}
