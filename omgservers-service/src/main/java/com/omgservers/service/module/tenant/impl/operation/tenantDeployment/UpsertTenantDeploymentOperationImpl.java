package com.omgservers.service.module.tenant.impl.operation.tenantDeployment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.service.event.body.module.tenant.TenantDeploymentCreatedEventBodyModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UpsertTenantDeploymentOperationImpl implements UpsertTenantDeploymentOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantDeploymentModel tenantDeployment) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_deployment(
                            id, idempotency_key, tenant_id, stage_id, version_id, created, modified, queue_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantDeployment.getId(),
                        tenantDeployment.getIdempotencyKey(),
                        tenantDeployment.getTenantId(),
                        tenantDeployment.getStageId(),
                        tenantDeployment.getVersionId(),
                        tenantDeployment.getCreated().atOffset(ZoneOffset.UTC),
                        tenantDeployment.getModified().atOffset(ZoneOffset.UTC),
                        tenantDeployment.getQueueId(),
                        tenantDeployment.getDeleted()
                ),
                () -> new TenantDeploymentCreatedEventBodyModel(tenantDeployment.getTenantId(),
                        tenantDeployment.getId()),
                () -> null
        );
    }
}
