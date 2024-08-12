package com.omgservers.service.server.service.router;

import com.omgservers.service.server.service.router.dto.CloseClientConnectionRequest;
import com.omgservers.service.server.service.router.dto.CloseClientConnectionResponse;
import com.omgservers.service.server.service.router.dto.CloseServerConnectionRequest;
import com.omgservers.service.server.service.router.dto.CloseServerConnectionResponse;
import com.omgservers.service.server.service.router.dto.RouteServerConnectionRequest;
import com.omgservers.service.server.service.router.dto.RouteServerConnectionResponse;
import com.omgservers.service.server.service.router.dto.TransferClientBinaryMessageRequest;
import com.omgservers.service.server.service.router.dto.TransferClientBinaryMessageResponse;
import com.omgservers.service.server.service.router.dto.TransferClientTextMessageRequest;
import com.omgservers.service.server.service.router.dto.TransferClientTextMessageResponse;
import com.omgservers.service.server.service.router.dto.TransferServerBinaryMessageRequest;
import com.omgservers.service.server.service.router.dto.TransferServerBinaryMessageResponse;
import com.omgservers.service.server.service.router.dto.TransferServerTextMessageRequest;
import com.omgservers.service.server.service.router.dto.TransferServerTextMessageResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RouterService {

    Uni<RouteServerConnectionResponse> routeServerConnection(@Valid RouteServerConnectionRequest request);

    Uni<CloseClientConnectionResponse> closeClientConnection(@Valid CloseClientConnectionRequest request);

    Uni<CloseServerConnectionResponse> closeServerConnection(@Valid CloseServerConnectionRequest request);

    Uni<TransferServerTextMessageResponse> transferServerTextMessage(@Valid TransferServerTextMessageRequest request);

    Uni<TransferClientTextMessageResponse> transferClientTextMessage(@Valid TransferClientTextMessageRequest request);

    Uni<TransferServerBinaryMessageResponse> transferServerBinaryMessage(@Valid TransferServerBinaryMessageRequest request);

    Uni<TransferClientBinaryMessageResponse> transferClientBinaryMessage(@Valid TransferClientBinaryMessageRequest request);
}
