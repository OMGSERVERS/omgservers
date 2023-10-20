package com.omgservers.module.runtime.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeMapper {

    final ObjectMapper objectMapper;

    public RuntimeModel fromRow(Row row) {
        final var runtime = new RuntimeModel();
        runtime.setId(row.getLong("id"));
        runtime.setCreated(row.getOffsetDateTime("created").toInstant());
        runtime.setModified(row.getOffsetDateTime("modified").toInstant());
        runtime.setTenantId(row.getLong("tenant_id"));
        runtime.setVersionId(row.getLong("version_id"));
        runtime.setType(RuntimeTypeEnum.valueOf(row.getString("type")));
        runtime.setDeleted(row.getBoolean("deleted"));
        try {
            runtime.setConfig(objectMapper.readValue(row.getString("config"), RuntimeConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("runtime can't be parsed, runtime=" + runtime, e);
        }
        return runtime;
    }
}
