package com.omgservers.service.handler;

import com.omgservers.model.dto.system.DeleteJobRequest;
import com.omgservers.model.dto.system.DeleteJobResponse;
import com.omgservers.model.dto.system.FindJobRequest;
import com.omgservers.model.dto.system.FindJobResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerDeletedEventBodyModel;
import com.omgservers.model.job.JobModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

        return matchmakerModule.getShortcutService().getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> {
                    log.info("Matchmaker was deleted, id={}", matchmakerId);

                    return deleteMatchmakerJob(matchmakerId)
                            .flatMap(wasJobDeleted -> matchmakerModule.getShortcutService()
                                    .deleteMatchmakerCommands((matchmakerId)))
                            .flatMap(voidItem -> matchmakerModule.getShortcutService()
                                    .deleteRequests(matchmakerId))
                            .flatMap(voidItem -> matchmakerModule.getShortcutService()
                                    .deleteMatches(matchmakerId));
                })
                .replaceWith(true);
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
}
