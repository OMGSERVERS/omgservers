package com.omgservers.service.shard.tenant.impl.operation.tenantStagePermission;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.service.operation.server.HasObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class VerifyTenantStagePermissionExistsOperationImpl implements VerifyTenantStagePermissionExistsOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> execute(final SqlConnection sqlConnection,
                                final int shard,
                                final Long tenantId,
                                final Long tenantStageId,
                                final Long userId,
                                final TenantStagePermissionQualifierEnum tenantStagePermissionQualifier) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $shard.tab_tenant_stage_permission
                        where tenant_id = $1 and stage_id = $2 and user_id = $3 and permission = $4 and deleted = false
                        limit 1
                        """,
                List.of(tenantId, tenantStageId, userId, tenantStagePermissionQualifier),
                "Tenant stage permission");
    }
}
