package com.omgservers.service.module.dispatcher.impl.service.dispatcherService;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleBinaryMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleClosedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleClosedConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleFailedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleFailedConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleOpenedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleOpenedConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleTextMessageResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DispatcherService {

    Uni<HandleOpenedConnectionResponse> handleOpenedConnection(@Valid HandleOpenedConnectionRequest request);

    Uni<HandleClosedConnectionResponse> handleClosedConnection(@Valid HandleClosedConnectionRequest request);

    Uni<HandleFailedConnectionResponse> handleFailedConnection(@Valid HandleFailedConnectionRequest request);

    Uni<HandleTextMessageResponse> handleTextMessage(@Valid HandleTextMessageRequest request);

    Uni<HandleBinaryMessageResponse> handleBinaryMessage(@Valid HandleBinaryMessageRequest request);
}
