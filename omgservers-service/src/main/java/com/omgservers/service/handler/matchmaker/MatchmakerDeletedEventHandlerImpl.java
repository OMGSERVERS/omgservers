package com.omgservers.service.handler.matchmaker;

import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequestRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequestResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchesRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchesResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsResponse;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRefResponse;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRefResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.matchmaker.MatchmakerDeletedEventBodyModel;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.request.MatchmakerRequestModel;
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

    Uni<List<MatchmakerRequestModel>> viewRequests(final Long matchmakerId) {
        final var request = new ViewMatchmakerRequestsRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().viewMatchmakerRequests(request)
                .map(ViewMatchmakerRequestsResponse::getMatchmakerRequests);
    }

    Uni<Boolean> deleteRequest(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchmakerRequestRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().deleteMatchmakerRequest(request)
                .map(DeleteMatchmakerRequestResponse::getDeleted);
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
                                            return Uni.createFrom().item(Boolean.FALSE);
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<MatchmakerMatchModel>> viewMatches(final Long matchmakerId) {
        final var request = new ViewMatchmakerMatchesRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().viewMatchmakerMatches(request)
                .map(ViewMatchmakerMatchesResponse::getMatchmakerMatches);
    }

    Uni<Boolean> deleteMatch(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchmakerMatchRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().deleteMatchmakerMatch(request)
                .map(DeleteMatchmakerMatchResponse::getDeleted);
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
