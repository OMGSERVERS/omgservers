package com.omgservers.module.user.impl.operation.upsertUser;

import com.omgservers.model.event.body.UserCreatedEventBodyModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UpsertUserOperationImpl implements UpsertUserOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertUser(final ChangeContext<?> changeContext,
                                   final SqlConnection sqlConnection,
                                   final int shard,
                                   final UserModel user) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_user(id, created, modified, role, password_hash)
                        values($1, $2, $3, $4, $5)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        user.getId(),
                        user.getCreated().atOffset(ZoneOffset.UTC),
                        user.getModified().atOffset(ZoneOffset.UTC),
                        user.getRole(),
                        user.getPasswordHash()),
                () -> new UserCreatedEventBodyModel(user.getId()),
                () -> logModelFactory.create("User was created, user=" + user)
        );
    }
}
