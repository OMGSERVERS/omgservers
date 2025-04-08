package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportResponse;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.module.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsResponse;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.shard.user.UserShard;
import com.omgservers.service.operation.alias.GetIdByProjectOperation;
import com.omgservers.service.operation.alias.GetIdByStageOperation;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantStagePermissionsMethodImpl implements DeleteTenantStagePermissionsMethod {

    final TenantShard tenantShard;
    final UserShard userShard;

    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;
    final GetIdByStageOperation getIdByStageOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteTenantStagePermissionsSupportResponse> execute(
            final DeleteTenantStagePermissionsSupportRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        return getIdByTenantOperation.execute(request.getTenant())
                .flatMap(tenantId -> getIdByProjectOperation.execute(tenantId, request.getProject())
                        .flatMap(tenantProjectId -> getIdByStageOperation.execute(tenantId,
                                        tenantProjectId,
                                        request.getStage())
                                .flatMap(tenantStageId -> {

                                    final var forUserId = request.getUserId();
                                    final var permissionsToDelete = request
                                            .getPermissionsToDelete();
                                    return deletePermissions(tenantId, tenantStageId, forUserId, permissionsToDelete)
                                            .invoke(deletedPermissions -> {
                                                if (deletedPermissions.size() > 0) {
                                                    log.info("The \"{}\" stage permissions in tenant \"{}\" " +
                                                                    "for user \"{}\" were deleted",
                                                            deletedPermissions.size(), tenantId, forUserId);
                                                }
                                            });
                                })))
                .map(DeleteTenantStagePermissionsSupportResponse::new);
    }

    Uni<List<TenantStagePermissionQualifierEnum>> deletePermissions(
            final Long tenantId,
            final Long tenantStageId,
            final Long forUserId,
            final Set<TenantStagePermissionQualifierEnum> permissionsToDelete) {

        return getUser(forUserId)
                .flatMap(user -> getTenant(tenantId)
                        .flatMap(tenant -> getTenantStage(tenantId, tenantStageId)
                                .flatMap(stage -> viewTenantStagePermissions(tenantId, tenantStageId)
                                        .flatMap(stagePermissions -> {
                                            final var userPermissionsToDelete =
                                                    stagePermissions.stream()
                                                            .filter(permission -> permission.getUserId()
                                                                    .equals(forUserId))
                                                            .filter(permission -> permissionsToDelete.contains(
                                                                    permission.getPermission()))
                                                            .toList();

                                            return Multi.createFrom().iterable(userPermissionsToDelete)
                                                    .onItem().transformToUniAndConcatenate(permission ->
                                                            deleteTenantStagePermission(tenantId, permission.getId())
                                                                    .map(deleted -> Tuple2.of(
                                                                            permission.getPermission(),
                                                                            deleted)))
                                                    .collect().asList();
                                        })))
                        .map(results -> results.stream().filter(Tuple2::getItem2)
                                .map(Tuple2::getItem1).toList())
                );
    }

    Uni<UserModel> getUser(final Long id) {
        final var request = new GetUserRequest(id);
        return userShard.getService().execute(request)
                .map(GetUserResponse::getUser);
    }

    Uni<TenantModel> getTenant(Long tenantId) {
        final var getTenantRequest = new GetTenantRequest(tenantId);
        return tenantShard.getService().execute(getTenantRequest)
                .map(GetTenantResponse::getTenant);
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<List<TenantStagePermissionModel>> viewTenantStagePermissions(final Long tenantId,
                                                                     final Long tenantStageId) {
        final var request = new ViewTenantStagePermissionsRequest(tenantId, tenantStageId);
        return tenantShard.getService().execute(request)
                .map(ViewTenantStagePermissionsResponse::getTenantStagePermissions);
    }

    Uni<Boolean> deleteTenantStagePermission(final Long tenantId, final Long id) {
        final var request = new DeleteTenantStagePermissionRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantStagePermissionResponse::getDeleted);
    }
}
