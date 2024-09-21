package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.GetTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.GetTenantMatchmakerRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantMatchmakerRequestCreatedEventBodyModel;
import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantMatchmakerRequestCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    final MatchmakerModelFactory matchmakerModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_MATCHMAKER_REQUEST_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantMatchmakerRequestCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionMatchmakerRequest(tenantId, id)
                .flatMap(versionMatchmakerRequest -> {
                    final var versionId = versionMatchmakerRequest.getDeploymentId();
                    final var matchmakerId = versionMatchmakerRequest.getMatchmakerId();
                    log.info("Version matchmaker request was created, id={}, version={}/{}, matchmakerId={}",
                            versionMatchmakerRequest.getId(),
                            tenantId,
                            versionId,
                            matchmakerId);

                    return syncMatchmaker(versionMatchmakerRequest, event.getIdempotencyKey());
                })
                .replaceWithVoid();
    }

    Uni<TenantMatchmakerRequestModel> getVersionMatchmakerRequest(final Long tenantId, final Long id) {
        final var request = new GetTenantMatchmakerRequestRequest(tenantId, id);
        return tenantModule.getTenantService().getVersionMatchmakerRequest(request)
                .map(GetTenantMatchmakerRequestResponse::getTenantMatchmakerRequest);
    }

    Uni<Boolean> syncMatchmaker(final TenantMatchmakerRequestModel versionMatchmakerRequest,
                                final String idempotencyKey) {
        final var tenantId = versionMatchmakerRequest.getTenantId();
        final var versionId = versionMatchmakerRequest.getDeploymentId();
        final var matchmakerId = versionMatchmakerRequest.getMatchmakerId();
        final var matchmaker = matchmakerModelFactory.create(matchmakerId,
                tenantId,
                versionId,
                idempotencyKey);
        final var request = new SyncMatchmakerRequest(matchmaker);
        return matchmakerModule.getMatchmakerService().syncMatchmaker(request)
                .map(SyncMatchmakerResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", matchmaker, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
