package com.omgservers.dispatcher.service.dispatcher;

import com.omgservers.dispatcher.service.dispatcher.dto.HandleBinaryMessageRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.HandleClosedConnectionRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.HandleExpiredConnectionsRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.HandleFailedConnectionRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.HandleOpenedConnectionRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.HandleTextMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DispatcherService {

    Uni<Void> handleOpenedConnection(@Valid HandleOpenedConnectionRequest request);

    Uni<Void> handleClosedConnection(@Valid HandleClosedConnectionRequest request);

    Uni<Void> handleFailedConnection(@Valid HandleFailedConnectionRequest request);

    Uni<Void> handleTextMessage(@Valid HandleTextMessageRequest request);

    Uni<Void> handleBinaryMessage(@Valid HandleBinaryMessageRequest request);

    Uni<Void> handleExpiredConnections(@Valid HandleExpiredConnectionsRequest request);
}
