package com.omgservers.module.tenant.impl.mapper;

import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionRuntimeModelMapper {

    public VersionRuntimeModel fromRow(Row row) {
        final var stageRuntime = new VersionRuntimeModel();
        stageRuntime.setId(row.getLong("id"));
        stageRuntime.setTenantId(row.getLong("tenant_id"));
        stageRuntime.setVersionId(row.getLong("version_id"));
        stageRuntime.setCreated(row.getOffsetDateTime("created").toInstant());
        stageRuntime.setModified(row.getOffsetDateTime("modified").toInstant());
        stageRuntime.setRuntimeId(row.getLong("runtime_id"));
        stageRuntime.setDeleted(row.getBoolean("deleted"));
        return stageRuntime;
    }
}
