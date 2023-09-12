package com.omgservers.module.tenant.impl.operation.upsertStage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.model.stage.StageModel;
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
class UpsertStageOperationImpl implements UpsertStageOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertStage(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final int shard,
                                    final Long tenantId,
                                    final StageModel stage) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_stage(
                            id, tenant_id, project_id, created, modified, secret, matchmaker_id, config)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        stage.getId(),
                        stage.getTenantId(),
                        stage.getProjectId(),
                        stage.getCreated().atOffset(ZoneOffset.UTC),
                        stage.getModified().atOffset(ZoneOffset.UTC),
                        stage.getSecret(),
                        stage.getMatchmakerId(),
                        getConfigString(stage)
                ),
                () -> new StageCreatedEventBodyModel(tenantId, stage.getId()),
                () -> logModelFactory.create(String.format("Stage was created, " +
                        "tenantId=%d, stage=%s", tenantId, stage))
        );
    }

    String getConfigString(StageModel stage) {
        try {
            return objectMapper.writeValueAsString(stage.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
