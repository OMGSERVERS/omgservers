package com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentRef;

import com.omgservers.schema.model.tenantDeploymentRef.TenantDeploymentRefModel;
import com.omgservers.service.event.body.module.tenant.TenantDeploymentRefCreatedEventBodyModel;
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
class UpsertTenantDeploymentRefOperationImpl implements UpsertTenantDeploymentRefOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantDeploymentRefModel tenantDeploymentRef) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, shard,
                """
                        insert into $shard.tab_tenant_deployment_ref(
                            id, idempotency_key, tenant_id, stage_id, version_id, created, modified, deployment_id,
                            deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantDeploymentRef.getId(),
                        tenantDeploymentRef.getIdempotencyKey(),
                        tenantDeploymentRef.getTenantId(),
                        tenantDeploymentRef.getStageId(),
                        tenantDeploymentRef.getVersionId(),
                        tenantDeploymentRef.getCreated().atOffset(ZoneOffset.UTC),
                        tenantDeploymentRef.getModified().atOffset(ZoneOffset.UTC),
                        tenantDeploymentRef.getDeploymentId(),
                        tenantDeploymentRef.getDeleted()
                ),
                () -> new TenantDeploymentRefCreatedEventBodyModel(tenantDeploymentRef.getTenantId(),
                        tenantDeploymentRef.getId()),
                () -> null
        );
    }
}
