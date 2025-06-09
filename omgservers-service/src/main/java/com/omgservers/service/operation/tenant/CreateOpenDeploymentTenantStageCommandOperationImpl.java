package com.omgservers.service.operation.tenant;

import com.omgservers.schema.model.tenantStageCommand.body.OpenDeploymentTenantStageCommandBodyDto;
import com.omgservers.schema.shard.tenant.tenantStageCommand.SyncTenantStageCommandRequest;
import com.omgservers.schema.shard.tenant.tenantStageCommand.SyncTenantStageCommandResponse;
import com.omgservers.service.factory.tenant.TenantStageCommandModelFactory;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateOpenDeploymentTenantStageCommandOperationImpl implements CreateOpenDeploymentTenantStageCommandOperation {

    final TenantShard tenantShard;

    final TenantStageCommandModelFactory tenantStageCommandModelFactory;

    @Override
    public Uni<Boolean> execute(final Long tenantId,
                                final Long tenantStageId,
                                final Long deploymentId) {
        final var commandBody = new OpenDeploymentTenantStageCommandBodyDto(deploymentId);

        final var idempotencyKey = commandBody.getQualifier() + "/" + deploymentId;
        final var deploymentCommand = tenantStageCommandModelFactory.create(tenantId,
                tenantStageId,
                commandBody,
                idempotencyKey);

        final var request = new SyncTenantStageCommandRequest(deploymentCommand);
        return tenantShard.getService().executeWithIdempotency(request)
                .map(SyncTenantStageCommandResponse::getCreated);
    }

    @Override
    public Uni<Boolean> executeFailSafe(final Long tenantId,
                                        final Long tenantStageId,
                                        final Long deploymentId) {
        return execute(tenantId, tenantStageId, deploymentId)
                .onFailure()
                .recoverWithItem(t -> {
                    log.warn("Failed, deploymentId={}, {}:{}",
                            deploymentId,
                            t.getClass().getSimpleName(),
                            t.getMessage());
                    return Boolean.FALSE;
                });
    }
}
