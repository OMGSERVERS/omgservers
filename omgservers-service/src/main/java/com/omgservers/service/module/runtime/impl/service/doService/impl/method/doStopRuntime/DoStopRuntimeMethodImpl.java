package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doStopRuntime;

import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.dto.runtime.DoStopRuntimeRequest;
import com.omgservers.model.dto.runtime.DoStopRuntimeResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.matchmakerCommand.body.StopMatchMatchmakerCommandBodyModel;
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
class DoStopRuntimeMethodImpl implements DoStopRuntimeMethod {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;

    final CheckShardOperation checkShardOperation;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;

    @Override
    public Uni<DoStopRuntimeResponse> doStopRuntime(final DoStopRuntimeRequest request) {
        log.debug("Do stop runtime, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    final var reason = request.getReason();
                    return doStopRuntime(runtimeId, reason);
                })
                .replaceWith(new DoStopRuntimeResponse());
    }

    Uni<Boolean> doStopRuntime(final Long runtimeId,
                               final String reason) {
        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    if (Objects.isNull(runtime.getConfig().getMatchConfig())) {
                        throw new ServerSideConflictException("Runtime is corrupted, matchConfig is null, " +
                                "runtimeId=" + runtimeId);
                    }

                    final var matchmakerId = runtime.getConfig().getMatchConfig().getMatchmakerId();
                    final var matchId = runtime.getConfig().getMatchConfig().getMatchId();
                    log.info("Stop was approved, runtimeId={}, matchmakerId={}, matchId={}, reason={}",
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
        final var commandBody = new StopMatchMatchmakerCommandBodyModel(matchId);
        final var commandModel = matchmakerCommandModelFactory.create(matchmakerId, commandBody);
        final var request = new SyncMatchmakerCommandRequest(commandModel);
        return matchmakerModule.getMatchmakerService().syncMatchmakerCommand(request)
                .map(SyncMatchmakerCommandResponse::getCreated);
    }
}
