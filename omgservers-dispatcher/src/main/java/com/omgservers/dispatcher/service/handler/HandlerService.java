package com.omgservers.dispatcher.service.handler;

import com.omgservers.dispatcher.service.handler.dto.HandleBinaryMessageRequest;
import com.omgservers.dispatcher.service.handler.dto.HandleClosedConnectionRequest;
import com.omgservers.dispatcher.service.handler.dto.HandleIdleConnectionsRequest;
import com.omgservers.dispatcher.service.handler.dto.HandleFailedConnectionRequest;
import com.omgservers.dispatcher.service.handler.dto.HandleOpenedConnectionRequest;
import com.omgservers.dispatcher.service.handler.dto.HandleTextMessageRequest;
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
