package com.omgservers.service.module.client.impl.operation.client.deleteClient;

import com.omgservers.schema.event.body.module.client.ClientDeletedEventBodyModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.module.client.impl.operation.client.selectClient.SelectClientOperation;
import com.omgservers.service.server.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

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
                                     final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_client
                        set modified = $2, deleted = true
                        where id = $1 and deleted = false
                        """,
                List.of(
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new ClientDeletedEventBodyModel(id),
                () -> null
        );
    }
}
