package com.omgservers.module.tenant.impl.operation.selectStage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.stage.StageConfigModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.operation.executeSelectObject.ExecuteSelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectStageOperationImpl implements SelectStageOperation {

    final ExecuteSelectObjectOperation executeSelectObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<StageModel> selectStage(final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long tenantId,
                                       final Long id) {
        return executeSelectObjectOperation.executeSelectObject(
                sqlConnection,
                shard,
                """
                        select id, tenant_id, project_id, created, modified, secret, matchmaker_id, config
                        from $schema.tab_tenant_stage
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                Arrays.asList(tenantId, id),
                "Stage",
                this::createStage);
    }

    StageModel createStage(Row row) {
        StageModel stage = new StageModel();
        stage.setId(row.getLong("id"));
        stage.setTenantId(row.getLong("tenant_id"));
        stage.setProjectId(row.getLong("project_id"));
        stage.setCreated(row.getOffsetDateTime("created").toInstant());
        stage.setModified(row.getOffsetDateTime("modified").toInstant());
        stage.setSecret(row.getString("secret"));
        stage.setMatchmakerId(row.getLong("matchmaker_id"));

        try {
            stage.setConfig(objectMapper.readValue(row.getString("config"), StageConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("stage config can't be parsed, stage=" + stage, e);
        }

        return stage;
    }
}
