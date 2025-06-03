package com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.method.interchangeMessagesMethod;

import com.omgservers.schema.entrypoint.runtime.InterchangeMessagesRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeMessagesRuntimeResponse;
import com.omgservers.schema.shard.runtime.runtimeMessage.InterchangeMessagesRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.InterchangeMessagesResponse;
import com.omgservers.service.operation.security.GetSecurityAttributeOperation;
import com.omgservers.service.shard.runtime.RuntimeShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class InterchangeMessagesMethodImpl implements InterchangeMessagesMethod {

    final RuntimeShard runtimeShard;

    final GetSecurityAttributeOperation getSecurityAttributeOperation;

    @Override
    public Uni<InterchangeMessagesRuntimeResponse> execute(final InterchangeMessagesRuntimeRequest request) {
        log.debug("Requested, {}", request);

        final var runtimeId = getSecurityAttributeOperation.getRuntimeId();

        final var outgoingMessages = request.getOutgoingMessages();
        final var consumedMessages = request.getConsumedMessages();

        final var interchangeMessagesRequest = new InterchangeMessagesRequest(runtimeId,
                outgoingMessages,
                consumedMessages);
        return runtimeShard.getService().execute(interchangeMessagesRequest)
                .map(InterchangeMessagesResponse::getIncomingMessages)
                .map(InterchangeMessagesRuntimeResponse::new);
    }
}
