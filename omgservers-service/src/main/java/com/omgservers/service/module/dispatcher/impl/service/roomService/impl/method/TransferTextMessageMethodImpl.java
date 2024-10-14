package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.HandleTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.MessageEncodingEnum;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.operation.TransferWebSocketMessageOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TransferTextMessageMethodImpl implements TransferTextMessageMethod {

    final TransferWebSocketMessageOperation transferWebSocketMessageOperation;

    @Override
    public Uni<Void> execute(final HandleTextMessageRequest request) {
        log.debug("Handle text message, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var message = request.getMessage();

        return transferWebSocketMessageOperation.execute(webSocketConnection, MessageEncodingEnum.TXT, message);
    }
}
