package com.omgservers.service.handler.tenant;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantStageDeletedEventBodyModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionProjectionModel;
import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionResponse;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsResponse;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsRequest;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsResponse;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
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
public class TenantStageDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_STAGE_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantStageDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var stageId = body.getId();

        return getStage(tenantId, stageId)
                .flatMap(stage -> {
                    log.info("Stage was deleted, {}/{}", tenantId, stageId);

                    return deleteStagePermissions(tenantId, stageId)
                            .flatMap(voidItem -> deleteVersions(tenantId, stageId));
                })
                .replaceWithVoid();
    }

    Uni<TenantStageModel> getStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantModule.getTenantService().getStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<Void> deleteStagePermissions(final Long tenantId, final Long stageId) {
        return viewStagePermissions(tenantId, stageId)
                .flatMap(stagePermissions -> Multi.createFrom().iterable(stagePermissions)
                        .onItem().transformToUniAndConcatenate(stagePermission ->
                                deleteStagePermission(tenantId, stagePermission.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete stage permission failed, " +
                                                            "stage={}/{}, " +
                                                            "stagePermissionId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    stageId,
                                                    stagePermission.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantStagePermissionModel>> viewStagePermissions(final Long tenantId, final Long stageId) {
        final var request = new ViewTenantStagePermissionsRequest(tenantId, stageId);
        return tenantModule.getTenantService().viewStagePermissions(request)
                .map(ViewTenantStagePermissionsResponse::getTenantStagePermissions);
    }

    Uni<Boolean> deleteStagePermission(final Long tenantId, final Long id) {
        final var request = new DeleteTenantStagePermissionRequest(tenantId, id);
        return tenantModule.getTenantService().deleteStagePermission(request)
                .map(DeleteTenantStagePermissionResponse::getDeleted);
    }

    Uni<Void> deleteVersions(final Long tenantId, final Long stageId) {
        return viewVersionProjections(tenantId, stageId)
                .flatMap(versionProjections -> Multi.createFrom().iterable(versionProjections)
                        .onItem().transformToUniAndConcatenate(versionProjection ->
                                deleteVersion(tenantId, versionProjection.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete version failed, " +
                                                            "stage={}/{}, " +
                                                            "versionId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    stageId,
                                                    versionProjection.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantVersionProjectionModel>> viewVersionProjections(final Long tenantId, final Long stageId) {
        final var request = new ViewTenantVersionsRequest(tenantId, stageId);
        return tenantModule.getTenantService().viewTenantVersions(request)
                .map(ViewTenantVersionsResponse::getTenantVersionProjections);
    }

    Uni<Boolean> deleteVersion(final Long tenantId, final Long id) {
        final var request = new DeleteTenantVersionRequest(tenantId, id);
        return tenantModule.getTenantService().deleteVersion(request)
                .map(DeleteTenantVersionResponse::getDeleted);
    }
}
