package com.omgservers.handler;

import com.omgservers.dto.matchmaker.DeleteMatchClientRequest;
import com.omgservers.dto.matchmaker.DeleteMatchClientResponse;
import com.omgservers.dto.matchmaker.GetMatchRequest;
import com.omgservers.dto.matchmaker.GetMatchResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.dto.matchmaker.SyncMatchClientRequest;
import com.omgservers.dto.matchmaker.SyncMatchClientResponse;
import com.omgservers.dto.matchmaker.ViewMatchClientsRequest;
import com.omgservers.dto.matchmaker.ViewMatchClientsResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchUpdatedEventBodyModel;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.matchmaker.factory.MatchClientModelFactory;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.runtime.factory.RuntimeModelFactory;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchUpdatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final SystemModule systemModule;
    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;

    final MatchClientModelFactory matchClientModelFactory;
    final RuntimeModelFactory runtimeModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_UPDATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchUpdatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getId();

        return getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> getMatch(matchmakerId, matchId)
                        .flatMap(match -> viewMatchClients(matchmakerId, matchId)
                                .flatMap(matchClients -> {
                                    final var matchClientByClientId = matchClients.stream()
                                            .collect(Collectors.toMap(MatchClientModel::getClientId,
                                                    Function.identity()));

                                    final var requests = match.getConfig().getGroups().stream()
                                            .flatMap(group -> group.getRequests().stream())
                                            .toList();

                                    final var requestByClientId = requests.stream()
                                            .collect(Collectors.toMap(RequestModel::getClientId,
                                                    Function.identity()));

                                    final var newRequests = requests.stream()
                                            .filter(request -> {
                                                final var clientId = request.getClientId();
                                                return !matchClientByClientId.containsKey(clientId);
                                            })
                                            .toList();

                                    final var deletedMatchClients = matchClients.stream()
                                            .filter(matchClient -> {
                                                final var clientId = matchClient.getClientId();
                                                return !requestByClientId.containsKey(clientId);
                                            })
                                            .toList();

                                    return createMatchClients(matchmakerId, matchId, newRequests)
                                            .flatMap(voidItem -> deleteMatchClients(matchmakerId, deletedMatchClients));
                                })
                        )
                )
                .replaceWith(true);
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<MatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().getMatch(request)
                .map(GetMatchResponse::getMatch);
    }

    Uni<List<MatchClientModel>> viewMatchClients(final Long matchmakerId, final Long matchId) {
        final var request = new ViewMatchClientsRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().viewMatchClients(request)
                .map(ViewMatchClientsResponse::getMatchClients);
    }

    Uni<Void> createMatchClients(final Long matchmakerId,
                                 final Long matchId,
                                 final List<RequestModel> requests) {
        return Multi.createFrom().iterable(requests)
                .onItem().transformToUniAndMerge(request -> {
                    final var userId = request.getUserId();
                    final var clientId = request.getClientId();
                    final var matchClient = matchClientModelFactory.create(matchmakerId,
                            matchId,
                            userId,
                            clientId);
                    final var syncMatchClientRequest = new SyncMatchClientRequest(matchClient);
                    return matchmakerModule.getMatchmakerService().syncMatchClient(syncMatchClientRequest)
                            .map(SyncMatchClientResponse::getCreated);
                })
                .collect().asList()
                .invoke(results -> {
                    if (results.size() > 0) {
                        log.info("Match clients were created after update, matchmakerId={}, count={}",
                                matchmakerId, results.size());
                    }
                })
                .replaceWithVoid();
    }

    Uni<Void> deleteMatchClients(final Long matchmakerId, final List<MatchClientModel> matchClients) {
        return Multi.createFrom().iterable(matchClients)
                .onItem().transformToUniAndMerge(matchClient -> {
                    final var request = new DeleteMatchClientRequest(matchClient.getMatchmakerId(),
                            matchClient.getId());
                    return matchmakerModule.getMatchmakerService().deleteMatchClient(request)
                            .map(DeleteMatchClientResponse::getDeleted);
                })
                .collect().asList()
                .invoke(results -> {
                    if (results.size() > 0) {
                        log.info("Match clients were deleted after match's update, matchmakerId={}, count={}",
                                matchmakerId, results.size());
                    }
                })
                .replaceWithVoid();
    }
}
