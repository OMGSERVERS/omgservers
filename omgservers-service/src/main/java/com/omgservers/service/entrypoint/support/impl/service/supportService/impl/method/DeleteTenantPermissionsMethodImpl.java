package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.module.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantResponse;
import com.omgservers.schema.module.tenant.tenantPermission.DeleteTenantPermissionRequest;
import com.omgservers.schema.module.tenant.tenantPermission.DeleteTenantPermissionResponse;
import com.omgservers.schema.module.tenant.tenantPermission.ViewTenantPermissionsRequest;
import com.omgservers.schema.module.tenant.tenantPermission.ViewTenantPermissionsResponse;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
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
class DeleteTenantPermissionsMethodImpl implements DeleteTenantPermissionsMethod {

    final TenantModule tenantModule;
    final UserModule userModule;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteTenantPermissionsSupportResponse> execute(
            final DeleteTenantPermissionsSupportRequest request) {
        log.debug("Requested, {}, principal={}", request,
                securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var forUserId = request.getUserId();
        final var tenantId = request.getTenantId();

        return getUser(forUserId)
                .flatMap(user -> getTenant(tenantId)
                        .flatMap(tenant -> viewTenantPermissions(tenantId)
                                .flatMap(tenantPermissions -> {
                                    final var requestPermissionToDelete = request.getPermissionsToDelete();

                                    final var userPermissionsToDelete = tenantPermissions.stream()
                                            .filter(permission -> permission.getUserId().equals(forUserId))
                                            .filter(permission -> requestPermissionToDelete.contains(
                                                    permission.getPermission()))
                                            .toList();

                                    return Multi.createFrom().iterable(userPermissionsToDelete)
                                            .onItem().transformToUniAndConcatenate(permission ->
                                                    deleteTenantPermission(tenantId, permission.getId())
                                                            .map(deleted -> Tuple2.of(permission.getPermission(),
                                                                    deleted)))
                                            .collect().asList();
                                })))
                .map(results -> results.stream().filter(Tuple2::getItem2).map(Tuple2::getItem1).toList())
                .invoke(deletedPermissions -> {
                    if (deletedPermissions.size() > 0) {
                        log.info("The {} tenant permissions in tenant {} for user {} were deleted by user {}",
                                deletedPermissions.size(), tenantId, forUserId, userId);
                    }
                })
                .map(DeleteTenantPermissionsSupportResponse::new);
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

    Uni<List<TenantPermissionModel>> viewTenantPermissions(final Long tenantId) {
        final var request = new ViewTenantPermissionsRequest(tenantId);
        return tenantModule.getService().viewTenantPermissions(request)
                .map(ViewTenantPermissionsResponse::getTenantPermissions);
    }

    Uni<Boolean> deleteTenantPermission(final Long tenantId, final Long id) {
        final var request = new DeleteTenantPermissionRequest(tenantId, id);
        return tenantModule.getService().deleteTenantPermission(request)
                .map(DeleteTenantPermissionResponse::getDeleted);
    }
}
