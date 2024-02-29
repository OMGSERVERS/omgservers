package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doStopMatchmaking;

import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.dto.runtime.DoStopMatchmakingRequest;
import com.omgservers.model.dto.runtime.DoStopMatchmakingResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.matchmakerCommand.body.StopMatchmakingCommandBodyModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.MatchmakerCommandModelFactory;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoStopMatchmakingMethodImpl implements DoStopMatchmakingMethod {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;

    final CheckShardOperation checkShardOperation;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;

    @Override
    public Uni<DoStopMatchmakingResponse> doStopMatchmaking(final DoStopMatchmakingRequest request) {
        log.debug("Do stop matchmaking, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    final var reason = request.getReason();
                    return doStopMatchmaking(runtimeId, reason);
                })
                .replaceWith(new DoStopMatchmakingResponse());
    }

    Uni<Boolean> doStopMatchmaking(final Long runtimeId,
                                   final String reason) {
        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    if (Objects.isNull(runtime.getConfig().getMatchConfig())) {
                        throw new ServerSideConflictException("Runtime is corrupted, matchConfig is null, " +
                                "runtimeId=" + runtimeId);
                    }

                    final var matchmakerId = runtime.getConfig().getMatchConfig().getMatchmakerId();
                    final var matchId = runtime.getConfig().getMatchConfig().getMatchId();
                    log.info("Do stop matchmaking, runtimeId={}, match={}/{}, reason={}",
                            runtimeId, matchmakerId, matchId, reason);

                    return syncStopMatchMatchmakerCommand(matchmakerId, matchId);
                });
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> syncStopMatchMatchmakerCommand(final Long matchmakerId, final Long matchId) {
        final var commandBody = new StopMatchmakingCommandBodyModel(matchId);
        final var commandModel = matchmakerCommandModelFactory.create(matchmakerId, commandBody);
        final var request = new SyncMatchmakerCommandRequest(commandModel);
        return matchmakerModule.getMatchmakerService().syncMatchmakerCommand(request)
                .map(SyncMatchmakerCommandResponse::getCreated);
    }
}
