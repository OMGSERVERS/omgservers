package com.omgservers.service.handler.tenant;

import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.model.dto.tenant.SyncVersionLobbyRequestRequest;
import com.omgservers.model.dto.tenant.SyncVersionLobbyRequestResponse;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequestResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.tenant.VersionCreatedEventBodyModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.tenant.VersionLobbyRequestModelFactory;
import com.omgservers.service.factory.tenant.VersionMatchmakerRequestModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.lobby.LobbyModule;
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
public class VersionCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    final VersionMatchmakerRequestModelFactory versionMatchmakerRequestModelFactory;
    final VersionLobbyRequestModelFactory versionLobbyRequestModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var versionId = body.getId();

        return getVersion(tenantId, versionId)
                .flatMap(version -> {
                    log.info("Version was created, version={}/{}, stageId={}, modes={}, files={}",
                            tenantId,
                            versionId,
                            version.getStageId(),
                            version.getConfig().getModes().stream().map(VersionModeModel::getName).toList(),
                            version.getSourceCode().getFiles().size());

                    final var idempotencyKey = event.getIdempotencyKey();

                    return syncVersionLobbyRequest(tenantId, versionId, idempotencyKey)
                            .flatMap(created -> syncVersionMatchmakerRequest(tenantId, versionId, idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<VersionModel> getVersion(final Long tenantId, final Long id) {
        final var request = new GetVersionRequest(tenantId, id);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }

    Uni<Boolean> syncVersionLobbyRequest(final Long tenantId,
                                         final Long versionId,
                                         final String idempotencyKey) {
        final var versionLobbyRequest = versionLobbyRequestModelFactory.create(tenantId, versionId, idempotencyKey);
        final var request = new SyncVersionLobbyRequestRequest(versionLobbyRequest);
        return tenantModule.getVersionService().syncVersionLobbyRequest(request)
                .map(SyncVersionLobbyRequestResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                            log.warn("Idempotency was violated, object={}, {}", versionLobbyRequest, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    Uni<Boolean> syncVersionMatchmakerRequest(final Long tenantId,
                                              final Long versionId,
                                              final String idempotencyKey) {
        final var versionMatchmaker = versionMatchmakerRequestModelFactory.create(tenantId, versionId, idempotencyKey);
        final var request = new SyncVersionMatchmakerRequestRequest(versionMatchmaker);
        return tenantModule.getVersionService().syncVersionMatchmakerRequest(request)
                .map(SyncVersionMatchmakerRequestResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                            log.warn("Idempotency was violated, object={}, {}", versionMatchmaker, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
