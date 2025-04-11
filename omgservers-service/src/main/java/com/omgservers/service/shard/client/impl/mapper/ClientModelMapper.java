package com.omgservers.service.shard.client.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.client.ClientConfigDto;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientModelMapper {

    final ObjectMapper objectMapper;

    public ClientModel fromRow(final Row row) {
        final var client = new ClientModel();
        client.setId(row.getLong("id"));
        client.setIdempotencyKey(row.getString("idempotency_key"));
        client.setCreated(row.getOffsetDateTime("created").toInstant());
        client.setModified(row.getOffsetDateTime("modified").toInstant());
        client.setUserId(row.getLong("user_id"));
        client.setPlayerId(row.getLong("player_id"));
        client.setDeploymentId(row.getLong("deployment_id"));
        client.setDeleted(row.getBoolean("deleted"));
        try {
            client.setConfig(objectMapper.readValue(row.getString("config"), ClientConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "client config can't be parsed, client=" + client, e);
        }
        return client;
    }
}
