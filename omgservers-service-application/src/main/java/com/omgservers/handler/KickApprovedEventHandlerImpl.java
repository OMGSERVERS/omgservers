package com.omgservers.handler;

import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.KickApprovedEventBodyModel;
import com.omgservers.model.matchmakerCommand.body.DeleteClientMatchmakerCommandBodyModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.factory.MatchmakerCommandModelFactory;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class KickApprovedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.KICK_APPROVED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (KickApprovedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var userId = body.getUserId();
        final var clientId = body.getClientId();

        log.info("Kick was approved, userId={}, clientId={}, runtimeId={}",
                userId, clientId, runtimeId);

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    if (Objects.isNull(runtime.getConfig().getMatchConfig())) {
                        throw new ServerSideConflictException("Runtime is corrupted, matchConfig is null, " +
                                "runtimeId=" + runtimeId);
                    }

                    final var matchmakerId = runtime.getConfig().getMatchConfig().getMatchmakerId();
                    return syncDeleteClientMatchmakerCommand(matchmakerId, clientId)
                            .replaceWithVoid();
                })
                .replaceWith(true);
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id, false);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> syncDeleteClientMatchmakerCommand(final Long matchmakerId,
                                                   final Long clientId) {
        final var commandBody = new DeleteClientMatchmakerCommandBodyModel(clientId);
        final var commandModel = matchmakerCommandModelFactory.create(matchmakerId, commandBody);
        final var request = new SyncMatchmakerCommandRequest(commandModel);
        return matchmakerModule.getMatchmakerService().syncMatchmakerCommand(request)
                .map(SyncMatchmakerCommandResponse::getCreated);
    }
}
