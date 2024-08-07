package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerResponse;
import com.omgservers.schema.module.tenant.GetVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.GetVersionMatchmakerRequestResponse;
import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.body.module.tenant.VersionMatchmakerRequestCreatedEventBodyModel;
import com.omgservers.schema.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
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
public class VersionMatchmakerRequestCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    final MatchmakerModelFactory matchmakerModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_MATCHMAKER_REQUEST_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionMatchmakerRequestCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionMatchmakerRequest(tenantId, id)
                .flatMap(versionMatchmakerRequest -> {
                    final var versionId = versionMatchmakerRequest.getVersionId();
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

    Uni<VersionMatchmakerRequestModel> getVersionMatchmakerRequest(final Long tenantId, final Long id) {
        final var request = new GetVersionMatchmakerRequestRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionMatchmakerRequest(request)
                .map(GetVersionMatchmakerRequestResponse::getVersionMatchmakerRequest);
    }

    Uni<Boolean> syncMatchmaker(final VersionMatchmakerRequestModel versionMatchmakerRequest,
                                final String idempotencyKey) {
        final var tenantId = versionMatchmakerRequest.getTenantId();
        final var versionId = versionMatchmakerRequest.getVersionId();
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
