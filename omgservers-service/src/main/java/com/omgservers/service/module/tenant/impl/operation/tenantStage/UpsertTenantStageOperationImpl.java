package com.omgservers.service.module.tenant.impl.operation.tenantStage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.service.event.body.module.tenant.TenantStageCreatedEventBodyModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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
class UpsertTenantStageOperationImpl implements UpsertTenantStageOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantStageModel tenantStage) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_stage(
                            id, idempotency_key, tenant_id, project_id, created, modified, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantStage.getId(),
                        tenantStage.getIdempotencyKey(),
                        tenantStage.getTenantId(),
                        tenantStage.getProjectId(),
                        tenantStage.getCreated().atOffset(ZoneOffset.UTC),
                        tenantStage.getModified().atOffset(ZoneOffset.UTC),
                        tenantStage.getDeleted()
                ),
                () -> new TenantStageCreatedEventBodyModel(tenantStage.getTenantId(), tenantStage.getId()),
                () -> null
        );
    }
}
