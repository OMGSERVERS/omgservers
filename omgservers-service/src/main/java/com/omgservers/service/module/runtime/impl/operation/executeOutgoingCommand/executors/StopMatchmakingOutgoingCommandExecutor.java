package com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.executors;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.schema.model.matchmakerCommand.body.ExcludeMatchMatchmakerCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.schema.model.outgoingCommand.body.StopMatchmakingOutgoingCommandBodyModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.service.factory.matchmaker.MatchmakerCommandModelFactory;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.OutgoingCommandExecutor;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StopMatchmakingOutgoingCommandExecutor implements OutgoingCommandExecutor {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;

    final CheckShardOperation checkShardOperation;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.STOP_MATCHMAKING;
    }

    @Override
    public Uni<Void> execute(final Long runtimeId, final OutgoingCommandModel outgoingCommand) {
        log.debug("Execute stop matchmaking outgoing command, outgoingCommand={}", outgoingCommand);

        final var commandBody = (StopMatchmakingOutgoingCommandBodyModel) outgoingCommand.getBody();
        final var reason = commandBody.getReason();

        return checkShardOperation.checkShard(runtimeId.toString())
                .flatMap(shard -> doStopMatchmaking(runtimeId, reason)
                        .replaceWithVoid());
    }

    Uni<Boolean> doStopMatchmaking(final Long runtimeId,
                                   final String reason) {
        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    final var matchmakerId = runtime.getConfig().getMatchConfig().getMatchmakerId();
                    final var matchId = runtime.getConfig().getMatchConfig().getMatchId();

                    log.info("Do stop matchmaking, runtimeId={}, match={}/{}, reason={}",
                            runtimeId, matchmakerId, matchId, reason);

                    return syncExcludeMatchMatchmakerCommand(matchmakerId, matchId);
                });
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> syncExcludeMatchMatchmakerCommand(final Long matchmakerId, final Long matchId) {
        final var commandBody = new ExcludeMatchMatchmakerCommandBodyModel(matchId);
        final var commandModel = matchmakerCommandModelFactory.create(matchmakerId, commandBody);
        final var request = new SyncMatchmakerCommandRequest(commandModel);
        return matchmakerModule.getMatchmakerService().syncMatchmakerCommand(request)
                .map(SyncMatchmakerCommandResponse::getCreated);
    }
}
