package com.omgservers.service.handler;

import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.StopCommandApprovedEventBodyModel;
import com.omgservers.model.matchmakerCommand.body.StopMatchMatchmakerCommandBodyModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.MatchmakerCommandModelFactory;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StopCommandApprovedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.STOP_COMMAND_APPROVED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (StopCommandApprovedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var reason = body.getReason();

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

                    return syncStopMatchMatchmakerCommand(matchmakerId, matchId)
                            .replaceWithVoid();
                })
                .replaceWith(true);
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id, false);
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
