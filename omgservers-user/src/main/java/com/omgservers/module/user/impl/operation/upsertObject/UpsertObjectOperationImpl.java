package com.omgservers.module.user.impl.operation.upsertObject;

import com.omgservers.model.object.ObjectModel;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChange.ExecuteChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertObjectOperationImpl implements UpsertObjectOperation {

    final ExecuteChangeOperation executeChangeOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertObject(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long userId,
                                     final ObjectModel object) {
        return executeChangeOperation.executeChange(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_user_object(id, player_id, created, modified, name, body)
                        values($1, $2, $3, $4, $5, $6)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        object.getId(),
                        object.getPlayerId(),
                        object.getCreated().atOffset(ZoneOffset.UTC),
                        object.getModified().atOffset(ZoneOffset.UTC),
                        object.getName(),
                        object.getBody()
                ),
                () -> null,
                () -> logModelFactory.create(String.format("Object was created, " +
                        "userId=%d, object=%s", userId, object))
        );
    }
}
