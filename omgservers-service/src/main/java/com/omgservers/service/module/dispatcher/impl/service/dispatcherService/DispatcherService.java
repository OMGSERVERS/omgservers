package com.omgservers.service.module.dispatcher.impl.service.dispatcherService;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleClosedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleFailedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleOpenedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleTextMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DispatcherService {

    Uni<Void> handleOpenedConnection(@Valid HandleOpenedConnectionRequest request);

    Uni<Void> handleClosedConnection(@Valid HandleClosedConnectionRequest request);

    Uni<Void> handleFailedConnection(@Valid HandleFailedConnectionRequest request);

    Uni<Void> handleTextMessage(@Valid HandleTextMessageRequest request);

    Uni<Void> handleBinaryMessage(@Valid HandleBinaryMessageRequest request);
}
