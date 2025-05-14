package com.omgservers.service.shard.tenant.impl.operation.tenantProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.service.event.body.module.tenant.TenantProjectCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertTenantProjectOperationImpl implements UpsertTenantProjectOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final TenantProjectModel tenantProject) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_tenant_project(
                            id, idempotency_key, tenant_id, created, modified, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantProject.getId(),
                        tenantProject.getIdempotencyKey(),
                        tenantProject.getTenantId(),
                        tenantProject.getCreated().atOffset(ZoneOffset.UTC),
                        tenantProject.getModified().atOffset(ZoneOffset.UTC),
                        getConfigString(tenantProject),
                        tenantProject.getDeleted()
                ),
                () -> new TenantProjectCreatedEventBodyModel(tenantProject.getTenantId(), tenantProject.getId()),
                () -> null
        );
    }

    String getConfigString(final TenantProjectModel tenantProject) {
        try {
            return objectMapper.writeValueAsString(tenantProject.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
