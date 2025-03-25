package com.omgservers.service.shard.tenant.impl.operation.tenantProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.service.event.body.module.tenant.TenantProjectCreatedEventBodyModel;
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
class UpsertTenantProjectOperationImpl implements UpsertTenantProjectOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantProjectModel tenantProject) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_project(
                            id, idempotency_key, tenant_id, created, modified, deleted)
                        values($1, $2, $3, $4, $5, $6)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantProject.getId(),
                        tenantProject.getIdempotencyKey(),
                        tenantProject.getTenantId(),
                        tenantProject.getCreated().atOffset(ZoneOffset.UTC),
                        tenantProject.getModified().atOffset(ZoneOffset.UTC),
                        tenantProject.getDeleted()
                ),
                () -> new TenantProjectCreatedEventBodyModel(tenantProject.getTenantId(), tenantProject.getId()),
                () -> null
        );
    }
}
