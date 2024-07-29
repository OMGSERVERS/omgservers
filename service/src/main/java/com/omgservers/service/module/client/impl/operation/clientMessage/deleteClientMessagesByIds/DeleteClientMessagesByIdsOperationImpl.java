package com.omgservers.service.module.client.impl.operation.clientMessage.deleteClientMessagesByIds;

import com.omgservers.service.factory.lobby.LogModelFactory;
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
class DeleteClientMessagesByIdsOperationImpl implements DeleteClientMessagesByIdsOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteClientMessagesByIds(final ChangeContext<?> changeContext,
                                                  final SqlConnection sqlConnection,
                                                  final int shard,
                                                  final Long clientId,
                                                  final List<Long> ids) {
        return changeObjectOperation.changeObject(changeContext, sqlConnection, shard,
                """
                        update $schema.tab_client_message
                        set modified = $3, deleted = true
                        where client_id = $1 and id = any($2) and deleted = false
                        """,
                List.of(
                        clientId,
                        ids.toArray(),
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> null,
                () -> null
        );
    }
}
