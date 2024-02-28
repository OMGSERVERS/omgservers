package com.omgservers.service.handler.job.task.match.operations.handleMatchCommand.matchCommandHandler;

import com.omgservers.model.dto.matchmaker.GetMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeClientRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeClientResponse;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.model.matchCommand.MatchCommandQualifierEnum;
import com.omgservers.model.matchCommand.body.AddClientMatchCommandBodyModel;
import com.omgservers.model.runtimeClient.RuntimeClientConfigModel;
import com.omgservers.service.factory.RuntimeClientModelFactory;
import com.omgservers.service.handler.job.task.match.operations.handleMatchCommand.MatchCommandHandler;
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
class AddClientMatchCommandHandlerImpl implements MatchCommandHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;

    final RuntimeClientModelFactory runtimeClientModelFactory;

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
        final var matchClient = body.getMatchClient();

        return getMatch(matchmakerId, matchId)
                .flatMap(match -> {
                    final var runtimeId = match.getRuntimeId();
                    return syncRuntimeClient(runtimeId, clientId, matchClient);
                })
                .replaceWithVoid();
    }

    Uni<MatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().getMatch(request)
                .map(GetMatchResponse::getMatch);
    }

    Uni<Boolean> syncRuntimeClient(final Long runtimeId,
                                   final Long clientId,
                                   final MatchClientModel matchClient) {
        final var runtimeClientConfig = RuntimeClientConfigModel.create();
        runtimeClientConfig.setMatchClient(matchClient);
        final var runtimeClient = runtimeClientModelFactory.create(runtimeId,
                clientId,
                runtimeClientConfig);
        final var request = new SyncRuntimeClientRequest(runtimeClient);
        return runtimeModule.getRuntimeService().syncRuntimeClient(request)
                .map(SyncRuntimeClientResponse::getCreated);
    }
}
