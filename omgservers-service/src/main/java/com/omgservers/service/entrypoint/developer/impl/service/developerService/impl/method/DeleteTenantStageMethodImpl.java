package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteStageDeveloperResponse;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.shard.tenant.tenantStage.DeleteTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.DeleteTenantStageResponse;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.operation.alias.GetIdByProjectOperation;
import com.omgservers.service.operation.alias.GetIdByStageOperation;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
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

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;
    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;
    final GetIdByStageOperation getIdByStageOperation;

    final TenantVersionModelFactory tenantVersionModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteStageDeveloperResponse> execute(final DeleteStageDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var project = request.getProject();
                    return getIdByProjectOperation.execute(tenantId, project)
                            .flatMap(tenantProjectId -> {
                                final var stage = request.getStage();
                                return getIdByStageOperation.execute(tenantId, tenantProjectId, stage)
                                        .flatMap(tenantStageId -> {
                                            final var permissionQualifier =
                                                    TenantProjectPermissionQualifierEnum.STAGE_MANAGER;
                                            return checkTenantProjectPermissionOperation.execute(tenantId,
                                                            tenantProjectId,
                                                            userId,
                                                            permissionQualifier)
                                                    .flatMap(voidItem -> deleteTenantStage(tenantId,
                                                            tenantStageId))
                                                    .invoke(deleted -> {
                                                        if (deleted) {
                                                            log.info("Stage \"{}\" was deleted " +
                                                                            "in tenant \"{}\"",
                                                                    tenantStageId, tenantId);
                                                        }
                                                    })
                                                    .map(DeleteStageDeveloperResponse::new);
                                        });
                            });
                });
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long tenantStageId) {
        final var request = new GetTenantStageRequest(tenantId, tenantStageId);
        return tenantShard.getService().execute(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<Boolean> deleteTenantStage(final Long tenantId,
                                   final Long id) {
        final var request = new DeleteTenantStageRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantStageResponse::getDeleted);
    }
}
