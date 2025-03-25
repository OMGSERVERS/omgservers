package com.omgservers.service.shard.runtime.impl.operation.handleOutgoingMessages.handlers;

import com.omgservers.schema.message.MessageModel;
import com.omgservers.schema.message.MessageQualifierEnum;
import com.omgservers.schema.message.body.MulticastMessageBodyDto;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.service.operation.client.CreateMessageProducedClientMessageOperation;
import com.omgservers.service.shard.runtime.impl.operation.handleOutgoingMessages.OutgoingMessageHandler;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MulticastMessageHandler implements OutgoingMessageHandler {

    final CreateMessageProducedClientMessageOperation createMessageProducedClientMessageOperation;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.MULTICAST_MESSAGE;
    }

    @Override
    public Uni<Void> execute(final RuntimeModel runtime,
                             final List<RuntimeAssignmentModel> runtimeAssignments,
                             final MessageModel outgoingMessage) {
        log.debug("Handle, {}", outgoingMessage);

        final var messageBody = (MulticastMessageBodyDto) outgoingMessage.getBody();
        final var clients = messageBody.getClients();
        final var message = messageBody.getMessage();

        final var onlyAssignedClients = runtimeAssignments.stream()
                .map(RuntimeAssignmentModel::getClientId)
                .filter(clients::contains)
                .toList();

        log.info("Multicast {}/{} clients", onlyAssignedClients.size(), clients.size());

        return Multi.createFrom().iterable(onlyAssignedClients)
                .onItem().transformToUniAndConcatenate(clientId -> createMessageProducedClientMessageOperation
                        .execute(clientId, message))
                .collect().asList()
                .replaceWithVoid();
    }
}
