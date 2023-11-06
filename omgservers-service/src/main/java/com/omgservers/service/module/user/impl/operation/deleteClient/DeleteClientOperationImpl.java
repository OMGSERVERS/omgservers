package com.omgservers.service.module.user.impl.operation.deleteClient;

import com.omgservers.model.event.body.ClientDeletedEventBodyModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.module.user.impl.operation.selectClient.SelectClientOperation;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
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
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_user_client
                        set modified = $3, deleted = true
                        where user_id = $1 and id = $2 and deleted = false
                        """,
                Arrays.asList(
                        userId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new ClientDeletedEventBodyModel(userId, id),
                () -> logModelFactory.create(String.format("Client was deleted, userId=%d, id=%d", userId, id))
        );
    }
}
