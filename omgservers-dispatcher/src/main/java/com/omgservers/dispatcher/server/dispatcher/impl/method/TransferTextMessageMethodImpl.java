package com.omgservers.dispatcher.server.dispatcher.impl.method;

import com.omgservers.dispatcher.server.dispatcher.dto.MessageEncodingEnum;
import com.omgservers.dispatcher.server.dispatcher.dto.TransferTextMessageRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.TransferTextMessageResponse;
import com.omgservers.dispatcher.server.dispatcher.impl.operation.TransferWebSocketMessageOperation;
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
    public Uni<TransferTextMessageResponse> execute(final TransferTextMessageRequest request) {
        log.debug("{}", request);

        final var dispatcherConnection = request.getDispatcherConnection();
        final var message = request.getMessage();

        return transferWebSocketMessageOperation.execute(dispatcherConnection, MessageEncodingEnum.TXT, message)
                .map(TransferTextMessageResponse::new);
    }
}
