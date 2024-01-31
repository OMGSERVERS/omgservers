package com.omgservers.service.module.client.impl.operation.deleteClientRuntime;

import com.omgservers.model.event.body.ClientRuntimeDeletedEventBodyModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.module.matchmaker.impl.operation.selectMatch.SelectMatchOperation;
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
class DeleteClientRuntimeOperationImpl implements DeleteClientRuntimeOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectMatchOperation selectMatchOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteClientRuntime(final ChangeContext<?> changeContext,
                                            final SqlConnection sqlConnection,
                                            final int shard,
                                            final Long clientId,
                                            final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_client_runtime
                        set modified = $3, deleted = true
                        where client_id = $1 and id = $2 and deleted = false
                        """,
                Arrays.asList(
                        clientId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new ClientRuntimeDeletedEventBodyModel(clientId, id),
                () -> null
        );
    }
}
