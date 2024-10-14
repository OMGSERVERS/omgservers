package com.omgservers.service.module.dispatcher.impl.service.routerService;

import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseClientConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseServerConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.RouteServerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.RouteServerConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientBinaryMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientTextMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseClientConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseServerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerBinaryMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerTextMessageResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RouterService {

    Uni<RouteServerConnectionResponse> routeServerConnection(@Valid RouteServerConnectionRequest request);

    Uni<CloseClientConnectionResponse> closeClientConnection(@Valid CloseClientConnectionRequest request);

    Uni<CloseServerConnectionResponse> closeServerConnection(@Valid CloseServerConnectionRequest request);

    Uni<TransferServerTextMessageResponse> transferServerTextMessage(@Valid TransferServerTextMessageRequest request);

    Uni<TransferClientTextMessageResponse> transferClientTextMessage(@Valid TransferClientTextMessageRequest request);

    Uni<TransferServerBinaryMessageResponse> transferServerBinaryMessage(@Valid
                                                                         TransferServerBinaryMessageRequest request);

    Uni<TransferClientBinaryMessageResponse> transferClientBinaryMessage(@Valid
                                                                         TransferClientBinaryMessageRequest request);
}
