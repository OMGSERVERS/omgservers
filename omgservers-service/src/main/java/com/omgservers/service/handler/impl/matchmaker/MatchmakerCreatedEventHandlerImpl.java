package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.schema.model.job.JobQualifierEnum;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.module.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.system.JobModelFactory;
import com.omgservers.service.factory.tenant.TenantMatchmakerRefModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.service.job.JobService;
import com.omgservers.service.service.job.dto.SyncJobRequest;
import com.omgservers.service.service.job.dto.SyncJobResponse;
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
    final TenantModule tenantModule;

    final JobService jobService;

    final TenantMatchmakerRefModelFactory tenantMatchmakerRefModelFactory;
    final EventModelFactory eventModelFactory;
    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getId();

        return getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> {
                    log.info("Matchmaker was created, id={}, deploymentId={}/{}",
                            matchmaker.getId(),
                            matchmaker.getTenantId(),
                            matchmaker.getDeploymentId());

                    final var idempotencyKey = event.getId().toString();

                    return syncTenantMatchmakerRef(matchmaker, idempotencyKey)
                            .flatMap(created -> syncMatchmakerJob(matchmakerId, idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerModule.getService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<Boolean> syncTenantMatchmakerRef(final MatchmakerModel matchmaker, final String idempotencyKey) {
        final var tenantId = matchmaker.getTenantId();
        final var deploymentId = matchmaker.getDeploymentId();
        final var matchmakerId = matchmaker.getId();
        final var tenantMatchmakerRef = tenantMatchmakerRefModelFactory.create(tenantId,
                deploymentId,
                matchmakerId,
                idempotencyKey);
        final var request = new SyncTenantMatchmakerRefRequest(tenantMatchmakerRef);
        return tenantModule.getService().syncTenantMatchmakerRefWithIdempotency(request)
                .map(SyncTenantMatchmakerRefResponse::getCreated)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(Boolean.FALSE);
    }

    Uni<Boolean> syncMatchmakerJob(final Long matchmakerId,
                                   final String idempotencyKey) {
        final var job = jobModelFactory.create(JobQualifierEnum.MATCHMAKER, matchmakerId, matchmakerId, idempotencyKey);

        final var syncEventRequest = new SyncJobRequest(job);
        return jobService.syncJobWithIdempotency(syncEventRequest)
                .map(SyncJobResponse::getCreated);
    }
}
