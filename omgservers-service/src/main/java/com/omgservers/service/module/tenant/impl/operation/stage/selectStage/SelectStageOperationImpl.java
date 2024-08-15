package com.omgservers.service.module.tenant.impl.operation.stage.selectStage;

import com.omgservers.schema.model.stage.StageModel;
import com.omgservers.service.module.tenant.impl.mapper.StageModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectStageOperationImpl implements SelectStageOperation {

    final SelectObjectOperation selectObjectOperation;

    final StageModelMapper stageModelMapper;

    @Override
    public Uni<StageModel> selectStage(final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long tenantId,
                                       final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, project_id, created, modified, secret, deleted
                        from $schema.tab_tenant_stage
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(
                        tenantId,
                        id
                ),
                "Stage",
                stageModelMapper::fromRow);
    }
}
