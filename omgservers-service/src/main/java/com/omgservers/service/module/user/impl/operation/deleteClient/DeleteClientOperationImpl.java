package com.omgservers.service.module.user.impl.operation.deleteClient;

import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.model.event.body.ClientDeletedEventBodyModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.module.user.impl.operation.selectClient.SelectClientOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteClientOperationImpl implements DeleteClientOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectClientOperation selectClientOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteClient(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long userId,
                                     final Long id) {
        return selectClientOperation.selectClient(sqlConnection, shard, userId, id)
                .flatMap(client -> changeObjectOperation.changeObject(
                        changeContext, sqlConnection, shard,
                        """
                                delete from $schema.tab_user_client
                                where user_id = $1 and id = $2
                                """,
                        Arrays.asList(userId, id),
                        () -> new ClientDeletedEventBodyModel(client),
                        () -> logModelFactory.create("Client was deleted, client=" + client)
                ))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(false);
    }
}
