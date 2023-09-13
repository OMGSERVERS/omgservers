package com.omgservers.module.user.impl.operation.deleteAttribute;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.module.user.impl.operation.selectAttribute.SelectAttributeOperation;
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
    final SelectAttributeOperation selectAttributeOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteAttribute(final ChangeContext<?> changeContext,
                                        final SqlConnection sqlConnection,
                                        final int shard,
                                        final Long userId,
                                        final Long playerId,
                                        final String name) {
        return selectAttributeOperation.selectAttribute(sqlConnection, shard, userId, playerId, name)
                .flatMap(attribute -> executeChangeObjectOperation.executeChangeObject(
                        changeContext, sqlConnection, shard,
                        """
                                delete from $schema.tab_user_attribute
                                where user_id = $1 and player_id = $2 and name = $3
                                """,
                        Arrays.asList(
                                userId,
                                playerId,
                                name
                        ),
                        () -> null,
                        () -> logModelFactory.create("Attribute was deleted, attribute=" + attribute)
                ))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(false);
    }
}
