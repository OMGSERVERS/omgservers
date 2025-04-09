package com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.service.event.body.module.tenant.TenantDeploymentResourceCreatedEventBodyModel;
import com.omgservers.service.factory.system.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertTenantDeploymentResourceOperationImpl implements UpsertTenantDeploymentResourceOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantDeploymentResourceModel tenantDeploymentResource) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, shard,
                """
                        insert into $shard.tab_tenant_deployment_resource(
                            id, idempotency_key, tenant_id, stage_id, version_id, created, modified, deployment_id,
                            status, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantDeploymentResource.getId(),
                        tenantDeploymentResource.getIdempotencyKey(),
                        tenantDeploymentResource.getTenantId(),
                        tenantDeploymentResource.getStageId(),
                        tenantDeploymentResource.getVersionId(),
                        tenantDeploymentResource.getCreated().atOffset(ZoneOffset.UTC),
                        tenantDeploymentResource.getModified().atOffset(ZoneOffset.UTC),
                        tenantDeploymentResource.getDeploymentId(),
                        tenantDeploymentResource.getStatus(),
                        tenantDeploymentResource.getDeleted()
                ),
                () -> new TenantDeploymentResourceCreatedEventBodyModel(tenantDeploymentResource.getTenantId(),
                        tenantDeploymentResource.getId()),
                () -> null
        );
    }
}
