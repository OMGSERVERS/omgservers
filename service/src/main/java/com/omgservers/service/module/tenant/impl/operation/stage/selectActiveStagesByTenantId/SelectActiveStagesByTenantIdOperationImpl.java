package com.omgservers.service.module.tenant.impl.operation.stage.selectActiveStagesByTenantId;

import com.omgservers.schema.model.stage.StageModel;
import com.omgservers.service.module.tenant.impl.mapper.StageModelMapper;
import com.omgservers.service.server.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveStagesByTenantIdOperationImpl implements SelectActiveStagesByTenantIdOperation {

    final SelectListOperation selectListOperation;

    final StageModelMapper stageModelMapper;

    @Override
    public Uni<List<StageModel>> selectActiveStagesByTenantId(final SqlConnection sqlConnection,
                                                              final int shard,
                                                              final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key,tenant_id, project_id, created, modified, secret, deleted
                        from $schema.tab_tenant_stage
                        where tenant_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(tenantId),
                "Stage",
                stageModelMapper::fromRow);
    }
}
