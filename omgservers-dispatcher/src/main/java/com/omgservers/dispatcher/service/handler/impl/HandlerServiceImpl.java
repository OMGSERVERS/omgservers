package com.omgservers.dispatcher.service.handler.impl;

import com.omgservers.dispatcher.service.handler.HandlerService;
import com.omgservers.dispatcher.service.handler.dto.HandleBinaryMessageRequest;
import com.omgservers.dispatcher.service.handler.dto.HandleClosedConnectionRequest;
import com.omgservers.dispatcher.service.handler.dto.HandleExpiredConnectionsRequest;
import com.omgservers.dispatcher.service.handler.dto.HandleFailedConnectionRequest;
import com.omgservers.dispatcher.service.handler.dto.HandleOpenedConnectionRequest;
import com.omgservers.dispatcher.service.handler.dto.HandleTextMessageRequest;
import com.omgservers.dispatcher.service.handler.impl.method.HandleBinaryMessageMethod;
import com.omgservers.dispatcher.service.handler.impl.method.HandleClosedConnectionMethod;
import com.omgservers.dispatcher.service.handler.impl.method.HandleExpiredConnectionsMethod;
import com.omgservers.dispatcher.service.handler.impl.method.HandleFailedConnectionMethod;
import com.omgservers.dispatcher.service.handler.impl.method.HandleOpenedConnectionMethod;
import com.omgservers.dispatcher.service.handler.impl.method.HandleTextMessageMethod;
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

    final HandleExpiredConnectionsMethod handleExpiredConnectionsMethod;
    final HandleOpenedConnectionMethod handleOpenedConnectionMethod;
    final HandleClosedConnectionMethod handleClosedConnectionMethod;
    final HandleFailedConnectionMethod handleFailedConnectionMethod;
    final HandleBinaryMessageMethod handleBinaryMessageMethod;
    final HandleTextMessageMethod handleTextMessageMethod;

    @Override
    public Uni<Void> handleOpenedConnection(@Valid final HandleOpenedConnectionRequest request) {
        return handleOpenedConnectionMethod.execute(request);
    }

    @Override
    public Uni<Void> handleClosedConnection(@Valid final HandleClosedConnectionRequest request) {
        return handleClosedConnectionMethod.execute(request);
    }

    @Override
    public Uni<Void> handleFailedConnection(@Valid final HandleFailedConnectionRequest request) {
        return handleFailedConnectionMethod.execute(request);
    }

    @Override
    public Uni<Void> handleTextMessage(@Valid final HandleTextMessageRequest request) {
        return handleTextMessageMethod.execute(request);
    }

    @Override
    public Uni<Void> handleBinaryMessage(@Valid final HandleBinaryMessageRequest request) {
        return handleBinaryMessageMethod.execute(request);
    }

    @Override
    public Uni<Void> handleExpiredConnections(@Valid final HandleExpiredConnectionsRequest request) {
        return handleExpiredConnectionsMethod.execute(request);
    }
}
