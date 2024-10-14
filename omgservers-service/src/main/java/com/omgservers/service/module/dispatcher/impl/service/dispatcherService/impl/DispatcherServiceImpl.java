package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.DispatcherService;
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
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method.HandleBinaryMessageMethod;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method.HandleClosedConnectionMethod;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method.HandleFailedConnectionMethod;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method.HandleOpenedConnectionMethod;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method.HandleTextMessageMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DispatcherServiceImpl implements DispatcherService {

    final HandleOpenedConnectionMethod handleOpenedConnectionMethod;
    final HandleClosedConnectionMethod handleClosedConnectionMethod;
    final HandleFailedConnectionMethod handleFailedConnectionMethod;
    final HandleBinaryMessageMethod handleBinaryMessageMethod;
    final HandleTextMessageMethod handleTextMessageMethod;

    @Override
    public Uni<HandleOpenedConnectionResponse> handleOpenedConnection(
            @Valid final HandleOpenedConnectionRequest request) {
        return handleOpenedConnectionMethod.execute(request);
    }

    @Override
    public Uni<HandleClosedConnectionResponse> handleClosedConnection(
            @Valid final HandleClosedConnectionRequest request) {
        return handleClosedConnectionMethod.execute(request);
    }

    @Override
    public Uni<HandleFailedConnectionResponse> handleFailedConnection(
            @Valid final HandleFailedConnectionRequest request) {
        return handleFailedConnectionMethod.execute(request);
    }

    @Override
    public Uni<HandleTextMessageResponse> handleTextMessage(
            @Valid final HandleTextMessageRequest request) {
        return handleTextMessageMethod.execute(request);
    }

    @Override
    public Uni<HandleBinaryMessageResponse> handleBinaryMessage(
            @Valid final HandleBinaryMessageRequest request) {
        return handleBinaryMessageMethod.execute(request);
    }
}
