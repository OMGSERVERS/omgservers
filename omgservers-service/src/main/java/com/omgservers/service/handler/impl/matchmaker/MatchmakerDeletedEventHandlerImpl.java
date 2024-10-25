package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
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
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.DeleteTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.DeleteTenantMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.FindTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.FindTenantMatchmakerRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerDeletedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.service.job.JobService;
import com.omgservers.service.service.job.dto.DeleteJobRequest;
import com.omgservers.service.service.job.dto.DeleteJobResponse;
import com.omgservers.service.service.job.dto.FindJobRequest;
import com.omgservers.service.service.job.dto.FindJobResponse;
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
                    log.info("Deleted, {}", matchmaker);

                    return deleteMatchmakerCommands(matchmakerId)
                            .flatMap(voidItem -> deleteRequests(matchmakerId))
                            .flatMap(voidItem -> deleteMatches(matchmakerId))
                            .flatMap(voidItem -> findAndDeleteTenantMatchmakerRef(matchmaker))
                            .flatMap(voidItem -> findAndDeleteJob(matchmakerId));
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerModule.getService().execute(request)
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
        return matchmakerModule.getService().execute(request)
                .map(ViewMatchmakerCommandsResponse::getMatchmakerCommands);
    }

    Uni<Boolean> deleteMatchmakerCommand(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchmakerCommandRequest(matchmakerId, id);
        return matchmakerModule.getService().execute(request)
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
        return matchmakerModule.getService().execute(request)
                .map(ViewMatchmakerRequestsResponse::getMatchmakerRequests);
    }

    Uni<Boolean> deleteRequest(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchmakerRequestRequest(matchmakerId, id);
        return matchmakerModule.getService().execute(request)
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
        return matchmakerModule.getService().execute(request)
                .map(ViewMatchmakerMatchesResponse::getMatchmakerMatches);
    }

    Uni<Boolean> deleteMatch(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchmakerMatchRequest(matchmakerId, id);
        return matchmakerModule.getService().execute(request)
                .map(DeleteMatchmakerMatchResponse::getDeleted);
    }

    Uni<Void> findAndDeleteTenantMatchmakerRef(final MatchmakerModel matchmaker) {
        final var tenantId = matchmaker.getTenantId();
        final var deploymentId = matchmaker.getDeploymentId();
        final var matchmakerId = matchmaker.getId();
        return findTenantMatchmakerRef(tenantId, deploymentId, matchmakerId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(this::deleteTenantMatchmakerRef)
                .replaceWithVoid();
    }

    Uni<TenantMatchmakerRefModel> findTenantMatchmakerRef(final Long tenantId,
                                                          final Long deploymentId,
                                                          final Long matchmakerId) {
        final var request = new FindTenantMatchmakerRefRequest(tenantId, deploymentId, matchmakerId);
        return tenantModule.getService().findTenantMatchmakerRef(request)
                .map(FindTenantMatchmakerRefResponse::getTenantMatchmakerRef);
    }

    Uni<Boolean> deleteTenantMatchmakerRef(final TenantMatchmakerRefModel tenantMatchmakerRef) {
        final var tenantId = tenantMatchmakerRef.getTenantId();
        final var id = tenantMatchmakerRef.getId();
        final var request = new DeleteTenantMatchmakerRefRequest(tenantId, id);
        return tenantModule.getService().deleteTenantMatchmakerRef(request)
                .map(DeleteTenantMatchmakerRefResponse::getDeleted);
    }

    Uni<Void> findAndDeleteJob(final Long tenantId) {
        return findJob(tenantId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(job -> deleteJob(job.getId()))
                .replaceWithVoid();
    }

    Uni<JobModel> findJob(final Long tenantId) {
        final var request = new FindJobRequest(tenantId, tenantId);
        return jobService.findJob(request)
                .map(FindJobResponse::getJob);
    }

    Uni<Boolean> deleteJob(final Long id) {
        final var request = new DeleteJobRequest(id);
        return jobService.deleteJob(request)
                .map(DeleteJobResponse::getDeleted);
    }
}
