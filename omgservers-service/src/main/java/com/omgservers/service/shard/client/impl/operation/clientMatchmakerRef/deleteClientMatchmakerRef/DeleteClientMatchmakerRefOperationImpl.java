package com.omgservers.service.shard.client.impl.operation.clientMatchmakerRef.deleteClientMatchmakerRef;

import com.omgservers.service.event.body.module.client.ClientMatchmakerRefDeletedEventBodyModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatch.SelectMatchmakerMatchOperation;
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
class DeleteClientMatchmakerRefOperationImpl implements DeleteClientMatchmakerRefOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectMatchmakerMatchOperation selectMatchmakerMatchOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteClientMatchmakerRef(final ChangeContext<?> changeContext,
                                                  final SqlConnection sqlConnection,
                                                  final int shard,
                                                  final Long clientId,
                                                  final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_client_matchmaker_ref
                        set modified = $3, deleted = true
                        where client_id = $1 and id = $2 and deleted = false
                        """,
                List.of(
                        clientId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new ClientMatchmakerRefDeletedEventBodyModel(clientId, id),
                () -> null
        );
    }
}
