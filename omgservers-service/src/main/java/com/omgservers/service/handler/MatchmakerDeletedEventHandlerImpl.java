package com.omgservers.service.handler;

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
import com.omgservers.model.dto.system.DeleteJobRequest;
import com.omgservers.model.dto.system.DeleteJobResponse;
import com.omgservers.model.dto.system.FindJobRequest;
import com.omgservers.model.dto.system.FindJobResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerDeletedEventBodyModel;
import com.omgservers.model.job.JobModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
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
    final SystemModule systemModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchmakerDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getId();

        return getDeletedMatchmaker(matchmakerId)
                .flatMap(matchmaker -> {
                    log.info("Matchmaker was deleted, id={}", matchmakerId);

                    return deleteMatchmakerJob(matchmakerId)
                            .flatMap(wasJobDeleted -> deleteMatchmakerCommands(matchmakerId))
                            .flatMap(voidItem -> deleteRequests(matchmakerId))
                            .flatMap(voidItem -> deleteMatches(matchmakerId));
                })
                .replaceWith(true);
    }

    Uni<MatchmakerModel> getDeletedMatchmaker(final Long id) {
        final var request = new GetMatchmakerRequest(id);
        return matchmakerModule.getMatchmakerService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<Boolean> deleteMatchmakerJob(final Long matchmakerId) {
        return findJob(matchmakerId)
                .flatMap(job -> {
                    final var request = new DeleteJobRequest(job.getId());
                    return systemModule.getJobService().deleteJob(request)
                            .map(DeleteJobResponse::getDeleted);
                });
    }

    Uni<JobModel> findJob(final Long matchmakerId) {
        final var request = new FindJobRequest(matchmakerId, matchmakerId, JobQualifierEnum.MATCHMAKER);
        return systemModule.getJobService().findJob(request)
                .map(FindJobResponse::getJob);
    }

    Uni<Void> deleteMatchmakerCommands(final Long matchmakerId) {
        return viewMatchmakerCommands(matchmakerId)
                .flatMap(matchmakerCommands -> Multi.createFrom().iterable(matchmakerCommands)
                        .onItem().transformToUniAndConcatenate(matchmakerCommand ->
                                deleteMatchmakerCommand(matchmakerId, matchmakerCommand.getId())
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
}
