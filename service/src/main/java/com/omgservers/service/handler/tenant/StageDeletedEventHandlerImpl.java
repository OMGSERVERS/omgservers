package com.omgservers.service.handler.tenant;

import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.body.module.tenant.StageDeletedEventBodyModel;
import com.omgservers.schema.model.stage.StageModel;
import com.omgservers.schema.model.stagePermission.StagePermissionModel;
import com.omgservers.schema.model.version.VersionProjectionModel;
import com.omgservers.schema.module.tenant.DeleteStagePermissionRequest;
import com.omgservers.schema.module.tenant.DeleteStagePermissionResponse;
import com.omgservers.schema.module.tenant.DeleteVersionRequest;
import com.omgservers.schema.module.tenant.DeleteVersionResponse;
import com.omgservers.schema.module.tenant.GetStageRequest;
import com.omgservers.schema.module.tenant.GetStageResponse;
import com.omgservers.schema.module.tenant.ViewStagePermissionsRequest;
import com.omgservers.schema.module.tenant.ViewStagePermissionsResponse;
import com.omgservers.schema.module.tenant.ViewVersionsRequest;
import com.omgservers.schema.module.tenant.ViewVersionsResponse;
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
public class StageDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.STAGE_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (StageDeletedEventBodyModel) event.getBody();
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

    Uni<StageModel> getStage(final Long tenantId, final Long id) {
        final var request = new GetStageRequest(tenantId, id);
        return tenantModule.getStageService().getStage(request)
                .map(GetStageResponse::getStage);
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

    Uni<List<StagePermissionModel>> viewStagePermissions(final Long tenantId, final Long stageId) {
        final var request = new ViewStagePermissionsRequest(tenantId, stageId);
        return tenantModule.getStageService().viewStagePermissions(request)
                .map(ViewStagePermissionsResponse::getStagePermissions);
    }

    Uni<Boolean> deleteStagePermission(final Long tenantId, final Long id) {
        final var request = new DeleteStagePermissionRequest(tenantId, id);
        return tenantModule.getStageService().deleteStagePermission(request)
                .map(DeleteStagePermissionResponse::getDeleted);
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

    Uni<List<VersionProjectionModel>> viewVersionProjections(final Long tenantId, final Long stageId) {
        final var request = new ViewVersionsRequest(tenantId, stageId);
        return tenantModule.getVersionService().viewVersions(request)
                .map(ViewVersionsResponse::getVersionProjections);
    }

    Uni<Boolean> deleteVersion(final Long tenantId, final Long id) {
        final var request = new DeleteVersionRequest(tenantId, id);
        return tenantModule.getVersionService().deleteVersion(request)
                .map(DeleteVersionResponse::getDeleted);
    }
}
