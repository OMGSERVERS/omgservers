package com.omgservers.service.shard.runtime.impl.operation.handleOutgoingMessages.handlers;

import com.omgservers.schema.message.MessageModel;
import com.omgservers.schema.message.MessageQualifierEnum;
import com.omgservers.schema.message.body.StopMatchmakingMessageBodyDto;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.service.operation.matchmaker.CreateCloseMatchMatchmakerCommandOperation;
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
public class StopMatchmakingMessageHandler implements OutgoingMessageHandler {

    final CreateCloseMatchMatchmakerCommandOperation createCloseMatchMatchmakerCommandOperation;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.STOP_MATCHMAKING;
    }

    @Override
    public Uni<Void> execute(final RuntimeModel runtime,
                             final List<RuntimeAssignmentModel> runtimeAssignments,
                             final MessageModel outgoingMessage) {
        log.debug("Handle, {}", outgoingMessage);

        final var messageBody = (StopMatchmakingMessageBodyDto) outgoingMessage.getBody();

        final var runtimeMatchConfig = runtime.getConfig().getMatch();
        final var matchmakerId = runtimeMatchConfig.getMatchmakerId();
        final var matchId = runtimeMatchConfig.getMatchId();

        log.info("Stop match \"{}\" matchmaking", matchId);

        return createCloseMatchMatchmakerCommandOperation.executeFailSafe(matchmakerId, matchId)
                .replaceWithVoid();
    }
}
