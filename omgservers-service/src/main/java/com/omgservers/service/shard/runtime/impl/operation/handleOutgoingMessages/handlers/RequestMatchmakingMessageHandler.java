package com.omgservers.service.shard.runtime.impl.operation.handleOutgoingMessages.handlers;

import com.omgservers.schema.message.MessageModel;
import com.omgservers.schema.message.MessageQualifierEnum;
import com.omgservers.schema.message.body.RequestMatchmakingMessageBodyDto;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.service.operation.matchmaker.CreateMatchmakerRequestOperation;
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
public class RequestMatchmakingMessageHandler implements OutgoingMessageHandler {

    final CreateMatchmakerRequestOperation createMatchmakerRequestOperation;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.REQUEST_MATCHMAKING;
    }

    @Override
    public Uni<Void> execute(final RuntimeModel runtime,
                             final List<RuntimeAssignmentModel> runtimeAssignments,
                             final MessageModel outgoingMessage) {
        log.debug("Handle, {}", outgoingMessage);

        final var messageBody = (RequestMatchmakingMessageBodyDto) outgoingMessage.getBody();
        final var clientId = messageBody.getClientId();
        final var mode = messageBody.getMode();

        final var clientAssigned = runtimeAssignments.stream()
                .anyMatch(runtimeAssignment -> runtimeAssignment.getClientId().equals(clientId));

        if (clientAssigned) {
            log.info("Request matchmaking for client \"{}\", mode={}", clientId, mode);

            final var deploymentId = runtime.getDeploymentId();
            return createMatchmakerRequestOperation.execute(deploymentId, clientId, mode)
                    .replaceWithVoid();
        } else {
            log.warn("Failed, client \"{}\" not assigned", clientId);
            return Uni.createFrom().voidItem();
        }
    }
}
