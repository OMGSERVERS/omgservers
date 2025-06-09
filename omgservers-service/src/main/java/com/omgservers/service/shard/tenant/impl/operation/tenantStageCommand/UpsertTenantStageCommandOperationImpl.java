package com.omgservers.service.shard.tenant.impl.operation.tenantStageCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandModel;
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
class UpsertTenantStageCommandOperationImpl implements UpsertTenantStageCommandOperation {

    final ChangeObjectOperation changeObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final TenantStageCommandModel tenantStageCommand) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_tenant_stage_command(
                            id, idempotency_key, tenant_id, stage_id, created, modified, qualifier, body, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantStageCommand.getId(),
                        tenantStageCommand.getIdempotencyKey(),
                        tenantStageCommand.getTenantId(),
                        tenantStageCommand.getStageId(),
                        tenantStageCommand.getCreated().atOffset(ZoneOffset.UTC),
                        tenantStageCommand.getModified().atOffset(ZoneOffset.UTC),
                        tenantStageCommand.getQualifier(),
                        getBodyString(tenantStageCommand),
                        tenantStageCommand.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }

    String getBodyString(final TenantStageCommandModel tenantStageCommand) {
        try {
            return objectMapper.writeValueAsString(tenantStageCommand.getBody());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
