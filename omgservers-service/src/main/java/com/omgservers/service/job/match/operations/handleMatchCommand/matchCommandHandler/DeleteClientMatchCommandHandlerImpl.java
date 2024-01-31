package com.omgservers.service.job.match.operations.handleMatchCommand.matchCommandHandler;

import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.model.matchCommand.MatchCommandQualifierEnum;
import com.omgservers.model.matchCommand.body.DeleteClientMatchCommandBodyModel;
import com.omgservers.service.factory.RuntimeCommandModelFactory;
import com.omgservers.service.job.match.operations.handleMatchCommand.MatchCommandHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteClientMatchCommandHandlerImpl implements MatchCommandHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final ClientModule clientModule;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Override
    public MatchCommandQualifierEnum getQualifier() {
        return MatchCommandQualifierEnum.DELETE_CLIENT;
    }

    @Override
    public Uni<Void> handle(MatchCommandModel matchCommand) {
        log.debug("Handle match command, {}", matchCommand);

        final var matchmakerId = matchCommand.getMatchmakerId();
        final var matchId = matchCommand.getMatchId();

        final var body = (DeleteClientMatchCommandBodyModel) matchCommand.getBody();
        final var clientId = body.getClientId();

        return matchmakerModule.getShortcutService().getMatch(matchmakerId, matchId)
                .flatMap(match -> {
                    final var runtimeId = match.getRuntimeId();
                    return clientModule.getShortcutService().findAndDeleteClientRuntime(clientId, runtimeId);
                })
                .replaceWithVoid();
    }
}
