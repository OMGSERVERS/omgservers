package com.omgservers.service.module.tenant.impl.operation.selectStage;

import com.omgservers.model.stage.StageModel;
import com.omgservers.service.module.tenant.impl.mapper.StageModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

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
                                       final Long id,
                                       final Boolean deleted) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, tenant_id, project_id, created, modified, secret, deleted
                        from $schema.tab_tenant_stage
                        where tenant_id = $1 and id = $2 and deleted = $3
                        limit 1
                        """,
                Arrays.asList(
                        tenantId,
                        id,
                        deleted
                ),
                "Stage",
                stageModelMapper::fromRow);
    }
}
