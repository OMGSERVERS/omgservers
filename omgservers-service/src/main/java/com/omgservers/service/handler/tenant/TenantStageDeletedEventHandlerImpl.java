package com.omgservers.service.handler.tenant;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionProjectionModel;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsResponse;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsRequest;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantStageDeletedEventBodyModel;
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
        final var id = body.getId();

        return getTenantStage(tenantId, id)
                .flatMap(tenantStage -> {
                    log.info("Tenant stage was deleted, tenantStage={}/{}", tenantId, id);

                    return deleteTenantStagePermissions(tenantId, id)
                            .flatMap(voidItem -> deleteTenantVersions(tenantId, id));
                })
                .replaceWithVoid();
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<Void> deleteTenantStagePermissions(final Long tenantId, final Long stageId) {
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
        return tenantModule.getTenantService().viewTenantStagePermissions(request)
                .map(ViewTenantStagePermissionsResponse::getTenantStagePermissions);
    }

    Uni<Boolean> deleteStagePermission(final Long tenantId, final Long id) {
        final var request = new DeleteTenantStagePermissionRequest(tenantId, id);
        return tenantModule.getTenantService().deleteTenantStagePermission(request)
                .map(DeleteTenantStagePermissionResponse::getDeleted);
    }

    Uni<Void> deleteTenantVersions(final Long tenantId, final Long tenantStageId) {
        return viewTenantVersionProjections(tenantId, tenantStageId)
                .flatMap(tenantVersionProjections -> Multi.createFrom().iterable(tenantVersionProjections)
                        .onItem().transformToUniAndConcatenate(tenantVersionProjection ->
                                deleteTenantVersion(tenantId, tenantVersionProjection.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete tenant version failed, " +
                                                            "tenantStage={}/{}, " +
                                                            "tenantVersionId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantStageId,
                                                    tenantVersionProjection.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantVersionProjectionModel>> viewTenantVersionProjections(final Long tenantId,
                                                                         final Long tenantStageId) {
        final var request = new ViewTenantVersionsRequest(tenantId, tenantStageId);
        return tenantModule.getTenantService().viewTenantVersions(request)
                .map(ViewTenantVersionsResponse::getTenantVersionProjections);
    }

    Uni<Boolean> deleteTenantVersion(final Long tenantId, final Long id) {
        final var request = new DeleteTenantVersionRequest(tenantId, id);
        return tenantModule.getTenantService().deleteTenantVersion(request)
                .map(DeleteTenantVersionResponse::getDeleted);
    }
}
