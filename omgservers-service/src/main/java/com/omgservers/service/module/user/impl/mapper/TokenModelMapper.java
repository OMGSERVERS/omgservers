package com.omgservers.service.module.user.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.token.TokenModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TokenModelMapper {

    final ObjectMapper objectMapper;

    public TokenModel fromRow(final Row row) {
        final var token = new TokenModel();
        token.setId(row.getLong("id"));
        token.setIdempotencyKey(row.getString("idempotency_key"));
        token.setUserId(row.getLong("user_id"));
        token.setCreated(row.getOffsetDateTime("created").toInstant());
        token.setModified(row.getOffsetDateTime("modified").toInstant());
        token.setExpire(row.getOffsetDateTime("expire").toInstant());
        token.setHash(row.getString("hash"));
        token.setDeleted(row.getBoolean("deleted"));
        return token;
    }
}
