package com.omgservers.module.user.impl.operation.selectObject;

import com.omgservers.model.object.ObjectModel;
import com.omgservers.operation.executeSelectObject.ExecuteSelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectObjectOperationImpl implements SelectObjectOperation {

    final ExecuteSelectObjectOperation executeSelectObjectOperation;

    @Override
    public Uni<ObjectModel> selectObject(final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long userId,
                                         final Long playerId,
                                         final String name) {
        return executeSelectObjectOperation.executeSelectObject(
                sqlConnection,
                shard,
                """
                        select id, user_id, player_id, created, modified, name, body
                        from $schema.tab_user_object
                        where user_id = $1 and player_id = $2 and name = $3
                        limit 1
                        """,
                Arrays.asList(userId, playerId, name),
                "Object",
                this::createObject);
    }

    ObjectModel createObject(Row row) {
        ObjectModel object = new ObjectModel();
        object.setId(row.getLong("id"));
        object.setUserId(row.getLong("user_id"));
        object.setPlayerId(row.getLong("player_id"));
        object.setCreated(row.getOffsetDateTime("created").toInstant());
        object.setModified(row.getOffsetDateTime("modified").toInstant());
        object.setName(row.getString("name"));
        object.setBody(row.getBuffer("body").getBytes());
        return object;
    }
}
