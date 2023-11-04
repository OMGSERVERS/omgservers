package com.omgservers.service.module.tenant.impl.operation.hasStagePermission;

import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.service.operation.hasObject.HasObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasStagePermissionOperationImpl implements HasStagePermissionOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> hasStagePermission(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long tenantId,
                                           final Long stageId,
                                           final Long userId,
                                           final StagePermissionEnum permission) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_tenant_stage_permission
                        where tenant_id = $1 and stage_id = $2 and user_id = $3 and permission = $4
                        limit 1
                        """,
                Arrays.asList(tenantId, stageId, userId, permission),
                "Stage permission");
    }
}
