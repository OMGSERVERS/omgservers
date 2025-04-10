package com.omgservers.service.shard.user.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UserModelMapper {

    final ObjectMapper objectMapper;

    public UserModel execute(final Row row) {
        final var user = new UserModel();
        user.setId(row.getLong("id"));
        user.setIdempotencyKey(row.getString("idempotency_key"));
        user.setCreated(row.getOffsetDateTime("created").toInstant());
        user.setModified(row.getOffsetDateTime("modified").toInstant());
        user.setRole(UserRoleEnum.valueOf(row.getString("role")));
        user.setPasswordHash(row.getString("password_hash"));
        user.setDeleted(row.getBoolean("deleted"));
        return user;
    }
}
