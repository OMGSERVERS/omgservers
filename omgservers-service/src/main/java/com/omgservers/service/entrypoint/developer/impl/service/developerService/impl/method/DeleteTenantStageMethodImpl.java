package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteStageDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.shard.tenant.tenantStage.DeleteTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.DeleteTenantStageResponse;
import com.omgservers.service.operation.alias.GetIdByStageOperation;
import com.omgservers.service.operation.authz.AuthorizeTenantProjectRequestOperation;
import com.omgservers.service.operation.security.GetSecurityAttributeOperation;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteTenantStageMethodImpl implements DeleteTenantStageMethod {

    final TenantShard tenantShard;

    final AuthorizeTenantProjectRequestOperation authorizeTenantProjectRequestOperation;
    final GetSecurityAttributeOperation getSecurityAttributeOperation;
    final GetIdByStageOperation getIdByStageOperation;

    @Override
    public Uni<DeleteStageDeveloperResponse> execute(final DeleteStageDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        final var project = request.getProject();
        final var userId = getSecurityAttributeOperation.getUserId();
        final var permission = TenantProjectPermissionQualifierEnum.STAGE_MANAGER;

        return authorizeTenantProjectRequestOperation.execute(tenant, project, userId, permission)
                .flatMap(authorization -> {
                    final var tenantId = authorization.tenantId();
                    final var tenantProjectId = authorization.tenantProjectId();
                    final var stage = request.getStage();
                    return getIdByStageOperation.execute(tenantId, tenantProjectId, stage)
                            .flatMap(tenantStageId -> deleteTenantStage(tenantId, tenantStageId)
                                    .invoke(deleted -> {
                                        if (deleted) {
                                            log.info("Delete stage \"{}\" in tenant \"{}\"",
                                                    tenantStageId, tenantId);
                                        }
                                    }));
                })
                .map(DeleteStageDeveloperResponse::new);
    }

    Uni<Boolean> deleteTenantStage(final Long tenantId,
                                   final Long id) {
        final var request = new DeleteTenantStageRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantStageResponse::getDeleted);
    }
}
