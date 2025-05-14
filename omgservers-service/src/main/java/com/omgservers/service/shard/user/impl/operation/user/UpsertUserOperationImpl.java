package com.omgservers.service.shard.user.impl.operation.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.service.event.body.module.user.UserCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UpsertUserOperationImpl implements UpsertUserOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final UserModel user) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_user(
                            id, idempotency_key, created, modified, role, password_hash, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        user.getId(),
                        user.getIdempotencyKey(),
                        user.getCreated().atOffset(ZoneOffset.UTC),
                        user.getModified().atOffset(ZoneOffset.UTC),
                        user.getRole(),
                        user.getPasswordHash(),
                        getConfigString(user),
                        user.getDeleted()
                ),
                () -> new UserCreatedEventBodyModel(user.getId()),
                () -> null
        );
    }

    String getConfigString(final UserModel user) {
        try {
            return objectMapper.writeValueAsString(user.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
