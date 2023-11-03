package com.omgservers.handler;

import com.omgservers.model.dto.system.SyncJobRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.factory.JobModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.tenant.factory.VersionMatchmakerModelFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final SystemModule systemModule;
    final TenantModule tenantModule;

    final VersionMatchmakerModelFactory versionMatchmakerModelFactory;
    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchmakerCreatedEventBodyModel) event.getBody();
        final var id = body.getId();

        return getMatchmaker(id)
                .flatMap(matchmaker -> {
                    log.info("Matchmaker was created, id={}, tenantId={}, versionId={}",
                            matchmaker.getId(),
                            matchmaker.getTenantId(),
                            matchmaker.getVersionId());
                    return syncVersionMatchmaker(matchmaker)
                            .flatMap(stageMatchmakerWasCreated -> syncMatchmakerJob(id));
                });
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<Boolean> syncVersionMatchmaker(MatchmakerModel matchmaker) {
        final var matchmakerId = matchmaker.getId();
        final var tenantId = matchmaker.getTenantId();
        final var versionId = matchmaker.getVersionId();

        final var stageMatchmaker = versionMatchmakerModelFactory.create(tenantId,
                versionId,
                matchmakerId);
        final var request = new SyncVersionMatchmakerRequest(stageMatchmaker);
        return tenantModule.getVersionService().syncVersionMatchmaker(request)
                .map(SyncVersionMatchmakerResponse::getCreated);
    }

    Uni<Boolean> syncMatchmakerJob(final Long matchmakerId) {
        final var job = jobModelFactory.create(matchmakerId, matchmakerId, JobQualifierEnum.MATCHMAKER);
        final var request = new SyncJobRequest(job);
        return systemModule.getJobService().syncJob(request)
                .replaceWith(true);
    }
}
