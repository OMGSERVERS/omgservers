package com.omgservers.module.runtime.impl.operation.selectRuntime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.operation.executeSelectObject.ExecuteSelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRuntimeOperationImpl implements SelectRuntimeOperation {

    final ExecuteSelectObjectOperation executeSelectObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<RuntimeModel> selectRuntime(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long id) {
        return executeSelectObjectOperation.executeSelectObject(
                sqlConnection,
                shard,
                """
                        select id, created, modified, tenant_id, stage_id, version_id, matchmaker_id, match_id, 
                            type, step, script_id, config
                        from $schema.tab_runtime
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "Runtime",
                this::createRuntime);
    }

    RuntimeModel createRuntime(Row row) {
        RuntimeModel runtime = new RuntimeModel();
        runtime.setId(row.getLong("id"));
        runtime.setCreated(row.getOffsetDateTime("created").toInstant());
        runtime.setModified(row.getOffsetDateTime("modified").toInstant());
        runtime.setTenantId(row.getLong("tenant_id"));
        runtime.setStageId(row.getLong("stage_id"));
        runtime.setVersionId(row.getLong("version_id"));
        runtime.setMatchmakerId(row.getLong("matchmaker_id"));
        runtime.setMatchId(row.getLong("match_id"));
        runtime.setType(RuntimeTypeEnum.valueOf(row.getString("type")));
        runtime.setStep(row.getLong("step"));
        runtime.setScriptId(row.getLong("script_id"));
        try {
            runtime.setConfig(objectMapper.readValue(row.getString("config"), RuntimeConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("runtime can't be parsed, runtime=" + runtime, e);
        }
        return runtime;
    }
}
