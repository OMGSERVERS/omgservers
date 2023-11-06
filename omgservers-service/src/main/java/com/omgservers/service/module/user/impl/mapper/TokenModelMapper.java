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

    public TokenModel fromRow(Row row) {
        final var tokenModel = new TokenModel();
        tokenModel.setId(row.getLong("id"));
        tokenModel.setUserId(row.getLong("user_id"));
        tokenModel.setCreated(row.getOffsetDateTime("created").toInstant());
        tokenModel.setModified(row.getOffsetDateTime("modified").toInstant());
        tokenModel.setExpire(row.getOffsetDateTime("expire").toInstant());
        tokenModel.setHash(row.getString("hash"));
        tokenModel.setDeleted(row.getBoolean("deleted"));
        return tokenModel;
    }
}
