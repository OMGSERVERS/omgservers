package com.omgservers.service.module.runtime.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.model.runtimePermission.RuntimePermissionModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimePermissionModelMapper {

    final ObjectMapper objectMapper;

    public RuntimePermissionModel fromRow(final Row row) {
        final var runtimePermission = new RuntimePermissionModel();
        runtimePermission.setId(row.getLong("id"));
        runtimePermission.setIdempotencyKey(row.getString("idempotency_key"));
        runtimePermission.setRuntimeId(row.getLong("runtime_id"));
        runtimePermission.setCreated(row.getOffsetDateTime("created").toInstant());
        runtimePermission.setModified(row.getOffsetDateTime("modified").toInstant());
        runtimePermission.setUserId(row.getLong("user_id"));
        runtimePermission.setPermission(RuntimePermissionEnum.valueOf(row.getString("permission")));
        runtimePermission.setDeleted(row.getBoolean("deleted"));
        return runtimePermission;
    }
}
