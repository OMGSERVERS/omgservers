package com.omgservers.module.runtime.impl.operation.upsertRuntime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.event.body.RuntimeCreatedEventBodyModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
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
class UpsertRuntimeOperationImpl implements UpsertRuntimeOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertRuntime(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final RuntimeModel runtime) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_runtime(
                            id, created, modified, tenant_id, stage_id, version_id, matchmaker_id, match_id,
                            type, step, script_id, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        runtime.getId(),
                        runtime.getCreated().atOffset(ZoneOffset.UTC),
                        runtime.getModified().atOffset(ZoneOffset.UTC),
                        runtime.getTenantId(),
                        runtime.getStageId(),
                        runtime.getVersionId(),
                        runtime.getMatchmakerId(),
                        runtime.getMatchId(),
                        runtime.getType(),
                        runtime.getStep(),
                        runtime.getScriptId(),
                        getConfigString(runtime),
                        runtime.getDeleted()
                ),
                () -> new RuntimeCreatedEventBodyModel(runtime.getId(), runtime.getMatchmakerId(), runtime.getMatchId()),
                () -> logModelFactory.create("Runtime was inserted, runtime=" + runtime)
        );
    }

    String getConfigString(RuntimeModel runtime) {
        try {
            return objectMapper.writeValueAsString(runtime.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
