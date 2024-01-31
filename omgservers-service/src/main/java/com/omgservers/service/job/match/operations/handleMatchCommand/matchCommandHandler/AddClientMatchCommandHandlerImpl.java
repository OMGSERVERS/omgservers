package com.omgservers.service.job.match.operations.handleMatchCommand.matchCommandHandler;

import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.model.matchCommand.MatchCommandQualifierEnum;
import com.omgservers.model.matchCommand.body.AddClientMatchCommandBodyModel;
import com.omgservers.service.factory.ClientRuntimeModelFactory;
import com.omgservers.service.job.match.operations.handleMatchCommand.MatchCommandHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AddClientMatchCommandHandlerImpl implements MatchCommandHandler {

    final MatchmakerModule matchmakerModule;
    final ClientModule clientModule;

    final ClientRuntimeModelFactory clientRuntimeModelFactory;

    @Override
    public MatchCommandQualifierEnum getQualifier() {
        return MatchCommandQualifierEnum.ADD_CLIENT;
    }

    @Override
    public Uni<Void> handle(MatchCommandModel matchCommand) {
        log.debug("Handle match command, {}", matchCommand);

        final var matchmakerId = matchCommand.getMatchmakerId();
        final var matchId = matchCommand.getMatchId();

        final var body = (AddClientMatchCommandBodyModel) matchCommand.getBody();
        final var clientId = body.getClientId();

        return matchmakerModule.getShortcutService().getMatch(matchmakerId, matchId)
                .flatMap(match -> {
                    final var runtimeId = match.getRuntimeId();
                    return syncClientRuntime(clientId, runtimeId);
                })
                .replaceWithVoid();
    }

    Uni<Boolean> syncClientRuntime(final Long clientId, final Long runtimeId) {
        final var clientRuntime = clientRuntimeModelFactory.create(clientId, runtimeId);
        return clientModule.getShortcutService().syncClientRuntime(clientRuntime);
    }
}
