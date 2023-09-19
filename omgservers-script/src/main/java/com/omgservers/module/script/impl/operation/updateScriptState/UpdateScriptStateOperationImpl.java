package com.omgservers.module.script.impl.operation.updateScriptState;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.ScriptUpdatedEventBodyModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
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
class UpdateScriptStateOperationImpl implements UpdateScriptStateOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> updateScriptState(final ChangeContext<?> changeContext,
                                          final SqlConnection sqlConnection,
                                          final int shard,
                                          final Long id,
                                          final String state) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_script
                        set modified = $2, state = $3
                        where id = $1
                        """,
                Arrays.asList(
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        state
                ),
                () -> new ScriptUpdatedEventBodyModel(id),
                () -> logModelFactory.create(String.format("Script's state was updated, " +
                        "id=%d, state=%s", id, state))
        );
    }
}
