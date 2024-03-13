package com.omgservers.service.module.tenant.impl.operation.selectActiveStagePermissionsByStageId;

import com.omgservers.model.stagePermission.StagePermissionModel;
import com.omgservers.service.module.tenant.impl.mapper.StagePermissionModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveStagePermissionsByStageIdOperationImpl
        implements SelectActiveStagePermissionsByStageIdOperation {

    final SelectListOperation selectListOperation;

    final StagePermissionModelMapper stagePermissionModelMapper;

    @Override
    public Uni<List<StagePermissionModel>> selectActiveStagePermissionsByStageId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId,
            final Long stageId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, tenant_id, stage_id, created, modified, user_id, permission, deleted
                        from $schema.tab_tenant_stage_permission
                        where tenant_id = $1 and stage_id = $2 and deleted = false
                        order by id asc
                        """,
                Arrays.asList(
                        tenantId,
                        stageId
                ),
                "Stage permission",
                stagePermissionModelMapper::fromRow);
    }
}
