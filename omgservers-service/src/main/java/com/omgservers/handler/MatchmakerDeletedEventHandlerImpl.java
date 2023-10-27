package com.omgservers.handler;

import com.omgservers.dto.internal.DeleteJobRequest;
import com.omgservers.dto.internal.DeleteJobResponse;
import com.omgservers.dto.tenant.DeleteVersionMatchmakerRequest;
import com.omgservers.dto.tenant.DeleteVersionMatchmakerResponse;
import com.omgservers.dto.tenant.FindVersionMatchmakerRequest;
import com.omgservers.dto.tenant.FindVersionMatchmakerResponse;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerDeletedEventBodyModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final SystemModule systemModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchmakerDeletedEventBodyModel) event.getBody();
        final var matchmaker = body.getMatchmaker();
        final var tenantId = matchmaker.getTenantId();
        final var versionId = matchmaker.getVersionId();
        final var matchmakerId = matchmaker.getId();

        log.info("Matchmaker was deleted, id={}, tenantId={}, versionId={}",
                matchmakerId,
                tenantId,
                versionId);

        return deleteMatchmakerJob(matchmakerId)
                .flatMap(wasJobDeleted -> findVersionMatchmaker(tenantId, matchmakerId)
                        .onFailure(ServerSideNotFoundException.class).recoverWithNull()
                        .onItem().ifNotNull().transformToUni(versionMatchmaker ->
                                deleteVersionMatchmaker(tenantId, versionMatchmaker.getId())
                        )
                )
                .replaceWith(true);
    }

    Uni<VersionMatchmakerModel> findVersionMatchmaker(final Long tenantId,
                                                      final Long matchmakerId) {
        final var request = new FindVersionMatchmakerRequest(tenantId, matchmakerId);
        return tenantModule.getVersionService().findVersionMatchmaker(request)
                .map(FindVersionMatchmakerResponse::getVersionMatchmaker);
    }

    Uni<Boolean> deleteVersionMatchmaker(final Long tenantId, final Long id) {
        final var request = new DeleteVersionMatchmakerRequest(tenantId, id);
        return tenantModule.getVersionService().deleteVersionMatchmaker(request)
                .map(DeleteVersionMatchmakerResponse::getDeleted);
    }

    Uni<Boolean> deleteMatchmakerJob(final Long matchmakerId) {
        final var request = new DeleteJobRequest(matchmakerId, matchmakerId, JobQualifierEnum.MATCHMAKER);
        return systemModule.getJobService().deleteJob(request)
                .map(DeleteJobResponse::getDeleted);
    }
}
