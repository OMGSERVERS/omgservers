package com.omgservers.dispatcher.service.handler;

import com.omgservers.dispatcher.service.handler.dto.HandleBinaryMessageRequest;
import com.omgservers.dispatcher.service.handler.dto.HandleClosedConnectionRequest;
import com.omgservers.dispatcher.service.handler.dto.HandleExpiredConnectionsRequest;
import com.omgservers.dispatcher.service.handler.dto.HandleFailedConnectionRequest;
import com.omgservers.dispatcher.service.handler.dto.HandleOpenedConnectionRequest;
import com.omgservers.dispatcher.service.handler.dto.HandleTextMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface HandlerService {

    Uni<Void> handleOpenedConnection(@Valid HandleOpenedConnectionRequest request);

    Uni<Void> handleClosedConnection(@Valid HandleClosedConnectionRequest request);

    Uni<Void> handleFailedConnection(@Valid HandleFailedConnectionRequest request);

    Uni<Void> handleTextMessage(@Valid HandleTextMessageRequest request);

    Uni<Void> handleBinaryMessage(@Valid HandleBinaryMessageRequest request);

    Uni<Void> handleExpiredConnections(@Valid HandleExpiredConnectionsRequest request);
}
