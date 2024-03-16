package com.omgservers.service.module.user.impl.operation.upsertUser;

import com.omgservers.model.event.body.UserCreatedEventBodyModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UpsertUserOperationImpl implements UpsertUserOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertUser(final ChangeContext<?> changeContext,
                                   final SqlConnection sqlConnection,
                                   final int shard,
                                   final UserModel user) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_user(
                            id, idempotency_key, created, modified, role, password_hash, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
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
                        user.getDeleted()
                ),
                () -> new UserCreatedEventBodyModel(user.getId()),
                () -> null
        );
    }
}
