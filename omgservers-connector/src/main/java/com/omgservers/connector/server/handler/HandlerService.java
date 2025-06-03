package com.omgservers.connector.server.handler;

import com.omgservers.connector.server.handler.dto.HandleBinaryMessageRequest;
import com.omgservers.connector.server.handler.dto.HandleClosedConnectionRequest;
import com.omgservers.connector.server.handler.dto.HandleFailedConnectionRequest;
import com.omgservers.connector.server.handler.dto.HandleIdleConnectionsRequest;
import com.omgservers.connector.server.handler.dto.HandleOpenedConnectionRequest;
import com.omgservers.connector.server.handler.dto.HandleTextMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface HandlerService {

    Uni<Void> execute(@Valid HandleOpenedConnectionRequest request);

    Uni<Void> execute(@Valid HandleClosedConnectionRequest request);

    Uni<Void> execute(@Valid HandleFailedConnectionRequest request);

    Uni<Void> execute(@Valid HandleTextMessageRequest request);

    Uni<Void> execute(@Valid HandleBinaryMessageRequest request);

    Uni<Void> execute(@Valid HandleIdleConnectionsRequest request);
}
