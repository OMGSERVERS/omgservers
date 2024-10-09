package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsResponse;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.user.UserModel;
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
class DeleteTenantStagePermissionsMethodImpl implements DeleteTenantStagePermissionsMethod {

    final TenantModule tenantModule;
    final UserModule userModule;

    @Override
    public Uni<DeleteTenantStagePermissionsSupportResponse> execute(
            final DeleteTenantStagePermissionsSupportRequest request) {
        log.debug("Delete stage permissions, request={}", request);

        final var userId = request.getUserId();
        final var tenantId = request.getTenantId();
        final var tenantStageId = request.getTenantStageId();

        return getUser(userId)
                .flatMap(user -> getTenant(tenantId)
                        .flatMap(tenant -> getTenantStage(tenantId, tenantStageId))
                        .flatMap(stage -> viewTenantStagePermissions(tenantId, tenantStageId)
                                .flatMap(stagePermissions -> {
                                    final var requestPermissionToDelete = request.getPermissionsToDelete();

                                    final var userPermissionsToDelete = stagePermissions.stream()
                                            .filter(permission -> permission.getUserId().equals(userId))
                                            .filter(permission -> requestPermissionToDelete.contains(
                                                    permission.getPermission()))
                                            .toList();

                                    return Multi.createFrom().iterable(userPermissionsToDelete)
                                            .onItem().transformToUniAndConcatenate(permission ->
                                                    deleteTenantStagePermission(tenantId, permission.getId())
                                                            .map(deleted -> Tuple2.of(permission.getPermission(),
                                                                    deleted)))
                                            .collect().asList();
                                })))
                .map(results -> results.stream().filter(Tuple2::getItem2).map(Tuple2::getItem1).toList())
                .map(DeleteTenantStagePermissionsSupportResponse::new);
    }

    Uni<UserModel> getUser(final Long id) {
        final var request = new GetUserRequest(id);
        return userModule.getService().getUser(request)
                .map(GetUserResponse::getUser);
    }

    Uni<TenantModel> getTenant(Long tenantId) {
        final var getTenantRequest = new GetTenantRequest(tenantId);
        return tenantModule.getService().getTenant(getTenantRequest)
                .map(GetTenantResponse::getTenant);
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantModule.getService().getTenantStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<List<TenantStagePermissionModel>> viewTenantStagePermissions(final Long tenantId,
                                                                     final Long tenantStageId) {
        final var request = new ViewTenantStagePermissionsRequest(tenantId, tenantStageId);
        return tenantModule.getService().viewTenantStagePermissions(request)
                .map(ViewTenantStagePermissionsResponse::getTenantStagePermissions);
    }

    Uni<Boolean> deleteTenantStagePermission(final Long tenantId, final Long id) {
        final var request = new DeleteTenantStagePermissionRequest(tenantId, id);
        return tenantModule.getService().deleteTenantStagePermission(request)
                .map(DeleteTenantStagePermissionResponse::getDeleted);
    }
}
