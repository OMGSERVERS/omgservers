package com.omgservers.module.user.impl.operation.deleteObject;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.module.user.impl.operation.selectObject.SelectObjectOperation;
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
class DeleteObjectOperationImpl implements DeleteObjectOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final SelectObjectOperation selectObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteObject(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long userId,
                                     final Long playerId,
                                     final String name) {
        return selectObjectOperation.selectObject(sqlConnection, shard, userId, playerId, name)
                .flatMap(object -> executeChangeObjectOperation.executeChangeObject(
                        changeContext, sqlConnection, shard,
                        """
                                delete from $schema.tab_user_object
                                where user_id = $1 and player_id = $2 and name = $3
                                """,
                        Arrays.asList(userId, playerId, name),
                        () -> null,
                        () -> logModelFactory.create("Object was deleted, object=" + object)
                ))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(false);
    }
}
