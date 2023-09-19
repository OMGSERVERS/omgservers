package com.omgservers.module.script.impl.operation.upsertScript;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.event.body.ScriptCreatedEventBodyModel;
import com.omgservers.model.script.ScriptModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertScriptOperationImpl implements UpsertScriptOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertScript(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final ScriptModel script) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_script(id, created, modified, tenant_id, version_id, type, state, config)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        script.getId(),
                        script.getCreated().atOffset(ZoneOffset.UTC),
                        script.getModified().atOffset(ZoneOffset.UTC),
                        script.getTenantId(),
                        script.getVersionId(),
                        script.getType(),
                        script.getState(),
                        getConfigString(script)
                ),
                () -> new ScriptCreatedEventBodyModel(script.getId()),
                () -> logModelFactory.create("Script was inserted, script=" + script)
        );
    }

    String getConfigString(ScriptModel script) {
        try {
            return objectMapper.writeValueAsString(script.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
