package com.omgservers.service.shard.tenant.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenant.TenantConfigDto;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantModelMapper {

    final ObjectMapper objectMapper;

    public TenantModel execute(final Row row) {
        final var tenant = new TenantModel();
        tenant.setId(row.getLong("id"));
        tenant.setCreated(row.getOffsetDateTime("created").toInstant());
        tenant.setModified(row.getOffsetDateTime("modified").toInstant());
        tenant.setIdempotencyKey(row.getString("idempotency_key"));
        tenant.setDeleted(row.getBoolean("deleted"));
        try {
            tenant.setConfig(objectMapper.readValue(row.getString("config"), TenantConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "tenant config can't be parsed, tenant=" + tenant, e);
        }
        return tenant;
    }
}
