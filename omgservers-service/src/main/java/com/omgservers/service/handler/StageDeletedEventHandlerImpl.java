package com.omgservers.service.handler;

import com.omgservers.model.dto.tenant.DeleteStagePermissionRequest;
import com.omgservers.model.dto.tenant.DeleteStagePermissionResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRequest;
import com.omgservers.model.dto.tenant.DeleteVersionResponse;
import com.omgservers.model.dto.tenant.GetStageRequest;
import com.omgservers.model.dto.tenant.GetStageResponse;
import com.omgservers.model.dto.tenant.ViewStagePermissionsRequest;
import com.omgservers.model.dto.tenant.ViewStagePermissionsResponse;
import com.omgservers.model.dto.tenant.ViewVersionsRequest;
import com.omgservers.model.dto.tenant.ViewVersionsResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.StageDeletedEventBodyModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.stagePermission.StagePermissionModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
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
    public Uni<Boolean> handle(EventModel event) {
        final var body = (StageDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var stageId = body.getId();

        return getDeletedStage(tenantId, stageId)
                .flatMap(stage -> {
                    log.info("Stage was deleted, {}/{}", tenantId, stageId);

                    return deleteStagePermissions(tenantId, stageId)
                            .flatMap(voidItem -> deleteStageVersions(tenantId, stageId));
                })
                .replaceWith(true);
    }

    Uni<Void> deleteStagePermissions(final Long tenantId, final Long stageId) {
        return viewStagePermissions(tenantId, stageId)
                .flatMap(stagePermissions -> Multi.createFrom().iterable(stagePermissions)
                        .onItem().transformToUniAndConcatenate(stagePermission ->
                                deleteStagePermission(tenantId, stagePermission.getId()))
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

    Uni<Void> deleteStageVersions(final Long tenantId, final Long stageId) {
        return viewVersions(tenantId, stageId)
                .flatMap(versions -> Multi.createFrom().iterable(versions)
                        .onItem().transformToUniAndConcatenate(version ->
                                deleteVersion(tenantId, version.getId()))
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<StageModel> getDeletedStage(final Long tenantId, final Long id) {
        final var request = new GetStageRequest(tenantId, id);
        return tenantModule.getStageService().getStage(request)
                .map(GetStageResponse::getStage);
    }

    Uni<List<VersionModel>> viewVersions(final Long tenantId, final Long stageId) {
        final var request = new ViewVersionsRequest(tenantId, stageId);
        return tenantModule.getVersionService().viewVersions(request)
                .map(ViewVersionsResponse::getVersions);
    }

    Uni<Boolean> deleteVersion(final Long tenantId, final Long id) {
        final var request = new DeleteVersionRequest(tenantId, id);
        return tenantModule.getVersionService().deleteVersion(request)
                .map(DeleteVersionResponse::getDeleted);
    }
}
