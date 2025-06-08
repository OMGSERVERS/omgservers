package com.omgservers.service.shard.tenant.impl.operation.tenantStageCommand;

import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandModel;
import com.omgservers.service.shard.tenant.impl.mapper.TenantStageCommandModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveTenantStageCommandsByTenantStageIdOperationImpl implements SelectActiveTenantStageCommandsByTenantStageIdOperation {

    final SelectListOperation selectListOperation;

    final TenantStageCommandModelMapper tenantStageCommandModelMapper;

    @Override
    public Uni<List<TenantStageCommandModel>> execute(final SqlConnection sqlConnection,
                                                     final int slot,
                                                     final Long tenantId,
                                                     final Long tenantStageId) {
        return selectListOperation.selectList(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, tenant_id, stage_id, created, modified, qualifier, body, deleted
                        from $slot.tab_tenant_stage_command
                        where tenant_id = $1 and stage_id = $2 and deleted = false
                        order by id asc
                        """,
                List.of(
                        tenantId,
                        tenantStageId
                ),
                "Tenant stage command",
                tenantStageCommandModelMapper::execute);
    }
}