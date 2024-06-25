package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteStagePermissions;

import com.omgservers.model.dto.support.DeleteStagePermissionsSupportRequest;
import com.omgservers.model.dto.support.DeleteStagePermissionsSupportResponse;
import com.omgservers.model.dto.tenant.DeleteStagePermissionRequest;
import com.omgservers.model.dto.tenant.DeleteStagePermissionResponse;
import com.omgservers.model.dto.tenant.GetStageRequest;
import com.omgservers.model.dto.tenant.GetStageResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.dto.tenant.ViewStagePermissionsRequest;
import com.omgservers.model.dto.tenant.ViewStagePermissionsResponse;
import com.omgservers.model.dto.user.GetUserRequest;
import com.omgservers.model.dto.user.GetUserResponse;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.stagePermission.StagePermissionModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteStagePermissionsMethodImpl implements DeleteStagePermissionsMethod {

    final TenantModule tenantModule;
    final UserModule userModule;

    @Override
    public Uni<DeleteStagePermissionsSupportResponse> deleteStagePermissions(
            final DeleteStagePermissionsSupportRequest request) {
        log.debug("Delete stage permissions, request={}", request);

        final var userId = request.getUserId();
        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();

        return getUser(userId)
                .flatMap(user -> getTenant(tenantId)
                        .flatMap(tenant -> getStage(tenantId, stageId))
                        .flatMap(stage -> viewStagePermissions(tenantId, stageId)
                                .flatMap(stagePermissions -> {
                                    final var requestPermissionToDelete = request.getPermissionsToDelete();

                                    final var userPermissionsToDelete = stagePermissions.stream()
                                            .filter(permission -> permission.getUserId().equals(userId))
                                            .filter(permission -> requestPermissionToDelete.contains(
                                                    permission.getPermission()))
                                            .toList();

                                    return Multi.createFrom().iterable(userPermissionsToDelete)
                                            .onItem().transformToUniAndConcatenate(permission ->
                                                    deleteStagePermission(tenantId, permission.getId())
                                                            .map(deleted -> Tuple2.of(permission.getPermission(),
                                                                    deleted)))
                                            .collect().asList();
                                })))
                .map(results -> results.stream().filter(Tuple2::getItem2).map(Tuple2::getItem1).toList())
                .map(DeleteStagePermissionsSupportResponse::new);
    }

    Uni<UserModel> getUser(final Long id) {
        final var request = new GetUserRequest(id);
        return userModule.getUserService().getUser(request)
                .map(GetUserResponse::getUser);
    }

    Uni<TenantModel> getTenant(Long tenantId) {
        final var getTenantRequest = new GetTenantRequest(tenantId);
        return tenantModule.getTenantService().getTenant(getTenantRequest)
                .map(GetTenantResponse::getTenant);
    }

    Uni<StageModel> getStage(final Long tenantId, final Long id) {
        final var request = new GetStageRequest(tenantId, id);
        return tenantModule.getStageService().getStage(request)
                .map(GetStageResponse::getStage);
    }

    Uni<List<StagePermissionModel>> viewStagePermissions(final Long tenantId,
                                                         final Long stageId) {
        final var request = new ViewStagePermissionsRequest(tenantId, stageId);
        return tenantModule.getStageService().viewStagePermissions(request)
                .map(ViewStagePermissionsResponse::getStagePermissions);
    }

    Uni<Boolean> deleteStagePermission(final Long tenantId, final Long id) {
        final var request = new DeleteStagePermissionRequest(tenantId, id);
        return tenantModule.getStageService().deleteStagePermission(request)
                .map(DeleteStagePermissionResponse::getDeleted);
    }
}
