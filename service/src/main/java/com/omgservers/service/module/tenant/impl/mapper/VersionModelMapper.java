package com.omgservers.service.module.tenant.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionModelMapper {

    final ObjectMapper objectMapper;

    public VersionModel fromRow(final Row row) {
        final var version = new VersionModel();
        version.setId(row.getLong("id"));
        version.setTenantId(row.getLong("tenant_id"));
        version.setStageId(row.getLong("stage_id"));
        version.setCreated(row.getOffsetDateTime("created").toInstant());
        version.setModified(row.getOffsetDateTime("modified").toInstant());
        version.setIdempotencyKey(row.getString("idempotency_key"));
        version.setDeleted(row.getBoolean("deleted"));
        try {
            version.setConfig(objectMapper.readValue(row.getString("config"), VersionConfigModel.class));
            version.setBase64Archive(Base64.getEncoder().encodeToString(row.getBuffer("archive").getBytes()));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "version can't be parsed, version=" + version, e);
        }

        return version;
    }
}
