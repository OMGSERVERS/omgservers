package com.omgservers.dispatcher.server.dispatcher.impl.method;

import com.omgservers.dispatcher.server.dispatcher.dto.MessageEncodingEnum;
import com.omgservers.dispatcher.server.dispatcher.dto.TransferBinaryMessageRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.TransferBinaryMessageResponse;
import com.omgservers.dispatcher.server.dispatcher.impl.operation.TransferWebSocketMessageOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TransferBinaryMessageMethodImpl implements TransferBinaryMessageMethod {

    final TransferWebSocketMessageOperation transferWebSocketMessageOperation;

    @Override
    public Uni<TransferBinaryMessageResponse> execute(final TransferBinaryMessageRequest request) {
        log.debug("{}", request);

        final var dispatcherConnection = request.getDispatcherConnection();
        final var buffer = request.getBuffer();
        final var message = Base64.getEncoder().encodeToString(buffer.getBytes());

        return transferWebSocketMessageOperation.execute(dispatcherConnection, MessageEncodingEnum.B64, message)
                .map(TransferBinaryMessageResponse::new);
    }
}
