package com.omgservers.service.handler.matchmaker;

import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.body.module.matchmaker.MatchmakerDeletedEventBodyModel;
import com.omgservers.schema.model.job.JobModel;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.schema.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequestResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchesRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchesResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerRequestsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerRequestsResponse;
import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.FindVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.FindVersionMatchmakerRefResponse;
import com.omgservers.schema.service.system.job.DeleteJobRequest;
import com.omgservers.schema.service.system.job.DeleteJobResponse;
import com.omgservers.schema.service.system.job.FindJobRequest;
import com.omgservers.schema.service.system.job.FindJobResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.server.service.job.JobService;
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

    final JobService jobService;

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
                            .flatMap(voidItem -> findAndDeleteVersionMatchmakerRef(matchmaker))
                            .flatMap(voidItem -> findAndDeleteJob(matchmakerId));
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

    Uni<Void> findAndDeleteJob(final Long tenantId) {
        return findJob(tenantId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(job -> deleteJob(job.getId()))
                .replaceWithVoid();
    }

    Uni<JobModel> findJob(final Long tenantId) {
        final var request = new FindJobRequest(tenantId);
        return jobService.findJob(request)
                .map(FindJobResponse::getJob);
    }

    Uni<Boolean> deleteJob(final Long id) {
        final var request = new DeleteJobRequest(id);
        return jobService.deleteJob(request)
                .map(DeleteJobResponse::getDeleted);
    }
}
