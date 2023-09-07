package com.omgservers.module.user.impl.operation.deleteAttribute;

import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteAttributeOperationImpl implements DeleteAttributeOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteAttribute(final ChangeContext<?> changeContext,
                                        final SqlConnection sqlConnection,
                                        final int shard,
                                        final Long userId,
                                        final Long playerId,
                                        final String name) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, shard,
                """
                        delete from $schema.tab_user_attribute
                        where user_id = $1 and player_id = $2 and attribute_name = $3
                        """,
                Arrays.asList(
                        userId,
                        playerId,
                        name
                ),
                () -> null,
                () -> logModelFactory.create(String.format("Attribute was deleted, " +
                        "userId=%d, playerId=%d, name=%s", userId, playerId, name))
        );
    }
}
