package com.omgservers.service.shard.matchmaker.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmaker.MatchmakerConfigDto;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerModelMapper {

    final ObjectMapper objectMapper;

    public MatchmakerModel execute(final Row row) {
        final var matchmaker = new MatchmakerModel();
        matchmaker.setId(row.getLong("id"));
        matchmaker.setIdempotencyKey(row.getString("idempotency_key"));
        matchmaker.setCreated(row.getOffsetDateTime("created").toInstant());
        matchmaker.setModified(row.getOffsetDateTime("modified").toInstant());
        matchmaker.setDeploymentId(row.getLong("deployment_id"));
        matchmaker.setDeleted(row.getBoolean("deleted"));
        try {
            matchmaker.setConfig(objectMapper.readValue(row.getString("config"), MatchmakerConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "matchmaker config can't be parsed, matchmaker=" + matchmaker, e);
        }
        return matchmaker;
    }
}
