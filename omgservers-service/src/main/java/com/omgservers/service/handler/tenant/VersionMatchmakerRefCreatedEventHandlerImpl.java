package com.omgservers.service.handler.tenant;

import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRequestResponse;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequestResponse;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRefResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.VersionMatchmakerRefCreatedEventBodyModel;
import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionMatchmakerRefCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_MATCHMAKER_REF_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionMatchmakerRefCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionMatchmakerRef(tenantId, id)
                .flatMap(versionMatchmakerRef -> {
                    final var versionId = versionMatchmakerRef.getVersionId();
                    final var matchmakerId = versionMatchmakerRef.getMatchmakerId();
                    log.info("Version matchmaker ref was created, id={}, version={}/{}, matchmakerId={}",
                            versionMatchmakerRef.getId(),
                            tenantId,
                            versionId,
                            matchmakerId);

                    return findAndDeleteVersionMatchmakerRequest(tenantId, versionId, matchmakerId);
                })
                .replaceWithVoid();
    }

    Uni<VersionMatchmakerRefModel> getVersionMatchmakerRef(final Long tenantId, final Long id) {
        final var request = new GetVersionMatchmakerRefRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionMatchmakerRef(request)
                .map(GetVersionMatchmakerRefResponse::getVersionMatchmakerRef);
    }

    Uni<Boolean> findAndDeleteVersionMatchmakerRequest(final Long tenantId,
                                                       final Long versionId,
                                                       final Long matchmakerId) {
        return findVersionMatchmakerRequest(tenantId, versionId, matchmakerId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(versionMatchmakerRequest ->
                        deleteVersionMatchmakerRequest(tenantId, versionMatchmakerRequest.getId()));
    }

    Uni<VersionMatchmakerRequestModel> findVersionMatchmakerRequest(final Long tenantId,
                                                                    final Long versionId,
                                                                    final Long matchmakerId) {
        final var request = new FindVersionMatchmakerRequestRequest(tenantId, versionId, matchmakerId);
        return tenantModule.getVersionService().findVersionMatchmakerRequest(request)
                .map(FindVersionMatchmakerRequestResponse::getVersionMatchmakerRequest);
    }

    Uni<Boolean> deleteVersionMatchmakerRequest(final Long tenantId, final Long id) {
        final var request = new DeleteVersionMatchmakerRequestRequest(tenantId, id);
        return tenantModule.getVersionService().deleteVersionMatchmakerRequest(request)
                .map(DeleteVersionMatchmakerRequestResponse::getDeleted);
    }
}
