package com.omgservers.service.module.runtime.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeGrantModelMapper {

    final ObjectMapper objectMapper;

    public RuntimeGrantModel fromRow(Row row) {
        final var runtimeGrant = new RuntimeGrantModel();
        runtimeGrant.setId(row.getLong("id"));
        runtimeGrant.setRuntimeId(row.getLong("runtime_id"));
        runtimeGrant.setCreated(row.getOffsetDateTime("created").toInstant());
        runtimeGrant.setModified(row.getOffsetDateTime("modified").toInstant());
        runtimeGrant.setShardKey(row.getLong("shard_key"));
        runtimeGrant.setEntityId(row.getLong("entity_id"));
        runtimeGrant.setType(RuntimeGrantTypeEnum.valueOf(row.getString("type")));
        return runtimeGrant;
    }
}
