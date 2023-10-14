package com.omgservers.handler;

import com.omgservers.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.dto.runtime.GetRuntimeRequest;
import com.omgservers.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.StopRequestedEventBodyModel;
import com.omgservers.model.matchmakerCommand.body.StopMatchMatchmakerCommandBodyModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.matchmaker.factory.MatchmakerCommandModelFactory;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StopRequestedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.STOP_REQUESTED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (StopRequestedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var reason = body.getReason();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    final var matchmakerId = runtime.getMatchmakerId();
                    final var matchId = runtime.getMatchId();

                    log.info("Stop was requested, matchmakerId={}, matchId={}, runtimeId={}",
                            matchmakerId, matchId, runtimeId);

                    return syncStopMatchMatchmakerCommand(matchmakerId, matchId)
                            .replaceWithVoid();
                })
                .replaceWith(true);
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
