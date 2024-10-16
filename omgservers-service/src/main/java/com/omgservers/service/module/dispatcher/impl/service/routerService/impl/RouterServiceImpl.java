package com.omgservers.service.module.dispatcher.impl.service.routerService.impl;

import com.omgservers.service.module.dispatcher.impl.service.routerService.RouterService;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseClientConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseClientConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseServerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseServerConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.RouteServerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.RouteServerConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientBinaryMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientTextMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerBinaryMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerTextMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.closeClientConnection.CloseClientConnectionMethod;
import com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.closeServerConnection.CloseServerConnectionMethod;
import com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.routeServerConnection.RouteServerConnectionMethod;
import com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.transferClientBinaryMessage.TransferClientBinaryMessageMethod;
import com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.transferClientTextMessage.TransferClientTextMessageMethod;
import com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.transferServerBinaryMessage.TransferServerBinaryMessageMethod;
import com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.transferServerTextMessage.TransferServerTextMessageMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RouterServiceImpl implements RouterService {

    final TransferServerBinaryMessageMethod transferServerBinaryMessageMethod;
    final TransferClientBinaryMessageMethod transferClientBinaryMessageMethod;
    final TransferServerTextMessageMethod transferServerTextMessageMethod;
    final TransferClientTextMessageMethod transferClientTextMessageMethod;
    final RouteServerConnectionMethod routeServerConnectionMethod;
    final CloseClientConnectionMethod closeClientConnectionMethod;
    final CloseServerConnectionMethod closeServerConnectionMethod;

    @Override
    public Uni<RouteServerConnectionResponse> routeServerConnection(@Valid final RouteServerConnectionRequest request) {
        return routeServerConnectionMethod.execute(request);
    }

    @Override
    public Uni<CloseClientConnectionResponse> closeClientConnection(@Valid final CloseClientConnectionRequest request) {
        return closeClientConnectionMethod.execute(request);
    }

    @Override
    public Uni<CloseServerConnectionResponse> closeServerConnection(@Valid final CloseServerConnectionRequest request) {
        return closeServerConnectionMethod.closeServerConnection(request);
    }

    @Override
    public Uni<TransferServerTextMessageResponse> transferServerTextMessage(
            @Valid final TransferServerTextMessageRequest request) {
        return transferServerTextMessageMethod.transferServerTextMessage(request);
    }

    @Override
    public Uni<TransferClientTextMessageResponse> transferClientTextMessage(
            @Valid final TransferClientTextMessageRequest request) {
        return transferClientTextMessageMethod.transferClientTextMessage(request);
    }

    @Override
    public Uni<TransferServerBinaryMessageResponse> transferServerBinaryMessage(
            @Valid final TransferServerBinaryMessageRequest request) {
        return transferServerBinaryMessageMethod.transferServerBinaryMessage(request);
    }

    @Override
    public Uni<TransferClientBinaryMessageResponse> transferClientBinaryMessage(
            @Valid final TransferClientBinaryMessageRequest request) {
        return transferClientBinaryMessageMethod.transferClientBinaryMessage(request);
    }
}
