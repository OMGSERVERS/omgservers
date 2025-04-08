package com.omgservers.service.shard.runtime.impl.operation.handleOutgoingMessages.handlers;

import com.omgservers.schema.message.MessageModel;
import com.omgservers.schema.message.MessageQualifierEnum;
import com.omgservers.schema.message.body.KickClientMessageBodyDto;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.module.client.client.DeleteClientRequest;
import com.omgservers.schema.module.client.client.DeleteClientResponse;
import com.omgservers.service.operation.matchmaker.CreateKickClientMatchmakerCommandOperation;
import com.omgservers.service.shard.client.ClientShard;
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
public class KickClientMessageHandler implements OutgoingMessageHandler {

    final ClientShard clientShard;

    final CreateKickClientMatchmakerCommandOperation createKickClientMatchmakerCommandOperation;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.KICK_CLIENT;
    }

    @Override
    public Uni<Void> execute(final RuntimeModel runtime,
                             final List<RuntimeAssignmentModel> runtimeAssignments,
                             final MessageModel outgoingMessage) {
        log.debug("Handle, {}", outgoingMessage);

        final var messageBody = (KickClientMessageBodyDto) outgoingMessage.getBody();
        final var clientId = messageBody.getClientId();

        final var clientAssigned = runtimeAssignments.stream()
                .anyMatch(runtimeAssignment -> runtimeAssignment.getClientId().equals(clientId));

        if (clientAssigned) {
            return kickClient(runtime, clientId);
        } else {
            log.warn("Failed, client \"{}\" not assigned", clientId);
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> kickClient(final RuntimeModel runtime,
                         final Long clientId) {
        return switch (runtime.getQualifier()) {
            case LOBBY -> {
                log.info("Kick lobby client \"{}\"", clientId);

                yield deleteClient(clientId)
                        .replaceWithVoid();
            }
            case MATCH -> {
                log.info("Kick match client \"{}\"", clientId);

                final var runtimeMatchConfig = runtime.getConfig().getMatch();
                final var matchmakerId = runtimeMatchConfig.getMatchmakerId();
                final var matchId = runtimeMatchConfig.getMatchId();

                yield createKickClientMatchmakerCommandOperation.executeFailSafe(matchmakerId, matchId, clientId)
                        .replaceWithVoid();
            }
        };
    }

    Uni<Boolean> deleteClient(final Long clientId) {
        final var request = new DeleteClientRequest(clientId);
        return clientShard.getService().execute(request)
                .map(DeleteClientResponse::getDeleted);
    }
}
