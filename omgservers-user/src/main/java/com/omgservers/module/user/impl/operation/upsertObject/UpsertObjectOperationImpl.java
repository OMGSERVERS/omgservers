package com.omgservers.module.user.impl.operation.upsertObject;

import com.omgservers.model.object.ObjectModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
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

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertObject(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long userId,
                                     final ObjectModel object) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_user_object(id, user_id, player_id, created, modified, name, body)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        object.getId(),
                        object.getUserId(),
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
