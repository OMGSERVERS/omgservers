package com.omgservers.service.module.tenant.impl.operation.upsertStage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertStageOperationImpl implements UpsertStageOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertStage(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final int shard,
                                    final StageModel stage) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_stage(
                            id, tenant_id, project_id, created, modified, secret, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
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
                        stage.getDeleted()
                ),
                () -> new StageCreatedEventBodyModel(stage.getTenantId(), stage.getId()),
                () -> logModelFactory.create(String.format("Stage was created, " +
                        "tenantId=%d, stage=%s", stage.getTenantId(), stage))
        );
    }
}
