package com.omgservers.service.shard.runtime.impl.operation.handleOutgoingMessages;

import com.omgservers.schema.message.MessageModel;
import com.omgservers.schema.message.MessageQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class HandleOutgoingMessageOperationImpl implements HandleOutgoingMessageOperation {

    final Map<MessageQualifierEnum, OutgoingMessageHandler> outgoingMessageHandlers;

    HandleOutgoingMessageOperationImpl(Instance<OutgoingMessageHandler> outgoingMessageHandlerBeans) {
        this.outgoingMessageHandlers = new ConcurrentHashMap<>();
        outgoingMessageHandlerBeans.stream().forEach(outgoingMessageHandler -> {
            final var qualifier = outgoingMessageHandler.getQualifier();
            if (outgoingMessageHandlers.put(qualifier, outgoingMessageHandler) != null) {
                log.error("Multiple \"{}\" handlers detected", qualifier);
            }
        });
    }

    @Override
    public Uni<Void> execute(final RuntimeModel runtime,
                             final List<RuntimeAssignmentModel> runtimeAssignments,
                             final MessageModel outgoingMessage) {
        final var runtimeId = runtime.getId();

        final var qualifier = outgoingMessage.getQualifier();
        final var qualifierBodyClass = qualifier.getBodyClass();
        final var body = outgoingMessage.getBody();

        if (!qualifierBodyClass.isInstance(body)) {
            log.warn("Qualifier \"{}\" and body class \"{}\" do not match, runtimeId={}",
                    qualifier, body.getClass().getSimpleName(), runtimeId);
            return Uni.createFrom().voidItem();
        }

        if (!outgoingMessageHandlers.containsKey(qualifier)) {
            log.error("Handler for \"{}\" not found, runtimeId={}", qualifier, runtimeId);
            return Uni.createFrom().voidItem();
        }

        return outgoingMessageHandlers.get(qualifier)
                .execute(runtime, runtimeAssignments, outgoingMessage);
    }
}
