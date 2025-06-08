package com.omgservers.service.shard.tenant.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandModel;
import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantStageCommandModelMapper {

    final ObjectMapper objectMapper;

    public TenantStageCommandModel execute(final Row row) {
        final var tenantStageCommand = new TenantStageCommandModel();
        tenantStageCommand.setId(row.getLong("id"));
        tenantStageCommand.setIdempotencyKey(row.getString("idempotency_key"));
        tenantStageCommand.setTenantId(row.getLong("tenant_id"));
        tenantStageCommand.setStageId(row.getLong("stage_id"));
        tenantStageCommand.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantStageCommand.setModified(row.getOffsetDateTime("modified").toInstant());
        final var qualifier = TenantStageCommandQualifierEnum.valueOf(row.getString("qualifier"));
        tenantStageCommand.setQualifier(qualifier);
        tenantStageCommand.setDeleted(row.getBoolean("deleted"));
        try {
            final var body = objectMapper.readValue(row.getString("body"), qualifier.getBodyClass());
            tenantStageCommand.setBody(body);
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "tenant stage command can't be parsed, tenantStageCommand=" + tenantStageCommand, e);
        }
        return tenantStageCommand;
    }
}
