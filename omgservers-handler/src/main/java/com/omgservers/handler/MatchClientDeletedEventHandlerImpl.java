package com.omgservers.handler;

import com.omgservers.dto.matchmaker.GetMatchRequest;
import com.omgservers.dto.matchmaker.GetMatchResponse;
import com.omgservers.dto.runtime.DeleteRuntimeGrantRequest;
import com.omgservers.dto.runtime.FindRuntimeGrantRequest;
import com.omgservers.dto.runtime.FindRuntimeGrantResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchClientDeletedEventBodyModel;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.runtimeCommand.body.DeleteClientRuntimeCommandBodyModel;
import com.omgservers.module.gateway.GatewayModule;
import com.omgservers.module.gateway.factory.MessageModelFactory;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.runtime.factory.RuntimeCommandModelFactory;
import com.omgservers.module.runtime.factory.RuntimeGrantModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchClientDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final GatewayModule gatewayModule;
    final UserModule userModule;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;
    final RuntimeGrantModelFactory runtimeGrantModelFactory;
    final MessageModelFactory messageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_CLIENT_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchClientDeletedEventBodyModel) event.getBody();
        final var matchClient = body.getMatchClient();

        final var clientId = matchClient.getClientId();
        final var matchmakerId = matchClient.getMatchmakerId();
        final var matchId = matchClient.getMatchId();
        return getMatch(matchmakerId, matchId)
                .flatMap(match -> {
                    final var runtimeId = match.getRuntimeId();
                    return deleteRuntimeGrant(runtimeId, clientId)
                            .flatMap(voidItem -> syncDeleteClientRuntimeCommand(runtimeId, clientId));
                })
                .replaceWith(true);
    }

    Uni<MatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().getMatch(request)
                .map(GetMatchResponse::getMatch);
    }

    Uni<Void> deleteRuntimeGrant(final Long runtimeId, final Long clientId) {
        final var findRuntimeGrantRequest = new FindRuntimeGrantRequest(runtimeId, clientId);
        return runtimeModule.getRuntimeService().findRuntimeGrant(findRuntimeGrantRequest)
                .map(FindRuntimeGrantResponse::getRuntimeGrant)
                .flatMap(runtimeGrant -> {
                    final var deleteRuntimeGrantRequest = new DeleteRuntimeGrantRequest(runtimeId,
                            runtimeGrant.getId());
                    return runtimeModule.getRuntimeService().deleteRuntimeGrant(deleteRuntimeGrantRequest);
                })
                .replaceWithVoid();
    }

    Uni<Void> syncDeleteClientRuntimeCommand(final Long runtimeId,
                                             final Long clientId) {
        final var commandBody = new DeleteClientRuntimeCommandBodyModel(clientId);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, commandBody);
        final var request = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeModule.getRuntimeService().syncRuntimeCommand(request)
                .replaceWithVoid();
    }
}
