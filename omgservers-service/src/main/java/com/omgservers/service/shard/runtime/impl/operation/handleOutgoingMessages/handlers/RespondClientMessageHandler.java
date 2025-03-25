package com.omgservers.service.shard.runtime.impl.operation.handleOutgoingMessages.handlers;

import com.omgservers.schema.message.MessageModel;
import com.omgservers.schema.message.MessageQualifierEnum;
import com.omgservers.schema.message.body.RespondClientMessageBodyDto;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.service.operation.client.CreateMessageProducedClientMessageOperation;
import com.omgservers.service.shard.runtime.impl.operation.handleOutgoingMessages.OutgoingMessageHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RespondClientMessageHandler implements OutgoingMessageHandler {

    final CreateMessageProducedClientMessageOperation createMessageProducedClientMessageOperation;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.RESPOND_CLIENT;
    }

    @Override
    public Uni<Void> execute(final RuntimeModel runtime,
                             final List<RuntimeAssignmentModel> runtimeAssignments,
                             final MessageModel outgoingMessage) {
        log.debug("Handle, {}", outgoingMessage);

        final var messageBody = (RespondClientMessageBodyDto) outgoingMessage.getBody();
        final var clientId = messageBody.getClientId();
        final var message = messageBody.getMessage();

        final var clientAssigned = runtimeAssignments.stream()
                .anyMatch(runtimeAssignment -> runtimeAssignment.getClientId().equals(clientId));

        if (clientAssigned) {
            log.info("Respond client \"{}\"", clientId);

            return createMessageProducedClientMessageOperation.execute(clientId, message)
                    .replaceWithVoid();
        } else {
            log.warn("Failed, client \"{}\" not assigned", clientId);
            return Uni.createFrom().voidItem();
        }
    }
}
