package com.omgservers.service.handler.matchmaker;

import com.omgservers.model.dto.matchmaker.DeleteMatchRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteRequestRequest;
import com.omgservers.model.dto.matchmaker.DeleteRequestResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchesRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchesResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewRequestsRequest;
import com.omgservers.model.dto.matchmaker.ViewRequestsResponse;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRefResponse;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRefResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerDeletedEventBodyModel;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getId();

        return getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> {
                    log.info("Matchmaker was deleted, id={}", matchmakerId);

                    return deleteMatchmakerCommands(matchmakerId)
                            .flatMap(voidItem -> deleteRequests(matchmakerId))
                            .flatMap(voidItem -> deleteMatches(matchmakerId))
                            .flatMap(voidItem -> findAndDeleteVersionMatchmakerRef(matchmaker));
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<Void> deleteMatchmakerCommands(final Long matchmakerId) {
        return viewMatchmakerCommands(matchmakerId)
                .flatMap(matchmakerCommands -> Multi.createFrom().iterable(matchmakerCommands)
                        .onItem().transformToUniAndConcatenate(matchmakerCommand ->
                                deleteMatchmakerCommand(matchmakerId, matchmakerCommand.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete matchmaker command failed, " +
                                                            "matchmakerId={}, " +
                                                            "matchmakerCommandId={}" +
                                                            "{}:{}",
                                                    matchmakerId,
                                                    matchmakerCommand.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<MatchmakerCommandModel>> viewMatchmakerCommands(final Long matchmakerId) {
        final var request = new ViewMatchmakerCommandsRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().viewMatchmakerCommands(request)
                .map(ViewMatchmakerCommandsResponse::getMatchmakerCommands);
    }

    Uni<Boolean> deleteMatchmakerCommand(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchmakerCommandRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().deleteMatchmakerCommand(request)
                .map(DeleteMatchmakerCommandResponse::getDeleted);
    }

    Uni<Void> deleteRequests(final Long matchmakerId) {
        return viewRequests(matchmakerId)
                .flatMap(requests -> Multi.createFrom().iterable(requests)
                        .onItem().transformToUniAndConcatenate(request ->
                                deleteRequest(matchmakerId, request.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete request failed, " +
                                                            "matchmakerId={}, " +
                                                            "requestId={}" +
                                                            "{}:{}",
                                                    matchmakerId,
                                                    request.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<RequestModel>> viewRequests(final Long matchmakerId) {
        final var request = new ViewRequestsRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().viewRequests(request)
                .map(ViewRequestsResponse::getRequests);
    }

    Uni<Boolean> deleteRequest(final Long matchmakerId, final Long id) {
        final var request = new DeleteRequestRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().deleteRequest(request)
                .map(DeleteRequestResponse::getDeleted);
    }

    Uni<Void> deleteMatches(final Long matchmakerId) {
        return viewMatches(matchmakerId)
                .flatMap(matches -> Multi.createFrom().iterable(matches)
                        .onItem().transformToUniAndConcatenate(match ->
                                deleteMatch(matchmakerId, match.getId())
                                        .onFailure()
                                        .recoverWithUni(t -> {
                                            log.warn("Delete match failed, " +
                                                            "matchmakerId={}, " +
                                                            "matchId={}" +
                                                            "{}:{}",
                                                    matchmakerId,
                                                    match.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return Uni.createFrom().item(false);
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<MatchModel>> viewMatches(final Long matchmakerId) {
        final var request = new ViewMatchesRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().viewMatches(request)
                .map(ViewMatchesResponse::getMatches);
    }

    Uni<Boolean> deleteMatch(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().deleteMatch(request)
                .map(DeleteMatchResponse::getDeleted);
    }

    Uni<Void> findAndDeleteVersionMatchmakerRef(final MatchmakerModel matchmaker) {
        final var tenantId = matchmaker.getTenantId();
        final var versionId = matchmaker.getVersionId();
        final var matchmakerId = matchmaker.getId();
        return findVersionMatchmakerRef(tenantId, versionId, matchmakerId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(this::deleteVersionMatchmakerRef)
                .replaceWithVoid();
    }

    Uni<VersionMatchmakerRefModel> findVersionMatchmakerRef(final Long tenantId,
                                                            final Long versionId,
                                                            final Long matchmakerId) {
        final var request = new FindVersionMatchmakerRefRequest(tenantId, versionId, matchmakerId);
        return tenantModule.getVersionService().findVersionMatchmakerRef(request)
                .map(FindVersionMatchmakerRefResponse::getVersionMatchmakerRef);
    }

    Uni<Boolean> deleteVersionMatchmakerRef(final VersionMatchmakerRefModel versionMatchmakerRef) {
        final var tenantId = versionMatchmakerRef.getTenantId();
        final var id = versionMatchmakerRef.getId();
        final var request = new DeleteVersionMatchmakerRefRequest(tenantId, id);
        return tenantModule.getVersionService().deleteVersionMatchmakerRef(request)
                .map(DeleteVersionMatchmakerRefResponse::getDeleted);
    }
}
