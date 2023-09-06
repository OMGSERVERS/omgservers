package com.omgservers.module.user.impl.operation.deleteAttribute;

import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChange.ExecuteChangeOperation;
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

    final ExecuteChangeOperation executeChangeOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteAttribute(final ChangeContext<?> changeContext,
                                        final SqlConnection sqlConnection,
                                        final int shard,
                                        final Long userId,
                                        final Long playerId,
                                        final String name) {
        return executeChangeOperation.executeChange(
                changeContext, sqlConnection, shard,
                """
                        delete from $schema.tab_user_attribute
                        where player_id = $1 and attribute_name = $2
                        """,
                Arrays.asList(
                        playerId,
                        name
                ),
                () -> null,
                () -> logModelFactory.create(String.format("Attribute was deleted, " +
                        "userId=%d, playerId=%d, name=%s", userId, playerId, name))
        );
    }
}
