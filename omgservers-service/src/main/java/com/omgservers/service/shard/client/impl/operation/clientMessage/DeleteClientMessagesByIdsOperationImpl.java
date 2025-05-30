package com.omgservers.service.shard.client.impl.operation.clientMessage;

import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
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

    @Override
    public Uni<Boolean> deleteClientMessagesByIds(final ChangeContext<?> changeContext,
                                                  final SqlConnection sqlConnection,
                                                  final int slot,
                                                  final Long clientId,
                                                  final List<Long> ids) {
        return changeObjectOperation.execute(changeContext, sqlConnection, slot,
                """
                        update $slot.tab_client_message
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
