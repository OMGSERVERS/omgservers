package com.omgservers.service.shard.client.impl.operation.clientRuntimeRef;

import com.omgservers.service.event.body.module.client.ClientRuntimeRefDeletedEventBodyModel;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource.SelectMatchmakerMatchResourceOperation;
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
class DeleteClientRuntimeRefOperationImpl implements DeleteClientRuntimeRefOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectMatchmakerMatchResourceOperation selectMatchmakerMatchResourceOperation;

    @Override
    public Uni<Boolean> deleteClientRuntimeRef(final ChangeContext<?> changeContext,
                                               final SqlConnection sqlConnection,
                                               final int slot,
                                               final Long clientId,
                                               final Long id) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        update $slot.tab_client_runtime_ref
                        set modified = $3, deleted = true
                        where client_id = $1 and id = $2 and deleted = false
                        """,
                List.of(
                        clientId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new ClientRuntimeRefDeletedEventBodyModel(clientId, id),
                () -> null
        );
    }
}
