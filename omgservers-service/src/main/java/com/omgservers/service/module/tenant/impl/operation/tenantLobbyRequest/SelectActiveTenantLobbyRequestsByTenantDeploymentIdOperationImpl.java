package com.omgservers.service.module.tenant.impl.operation.tenantLobbyRequest;

import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import com.omgservers.service.module.tenant.impl.mapper.TenantLobbyRequestModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveTenantLobbyRequestsByTenantDeploymentIdOperationImpl
        implements SelectActiveTenantLobbyRequestsByTenantDeploymentIdOperation {

    final SelectListOperation selectListOperation;

    final TenantLobbyRequestModelMapper tenantLobbyRequestModelMapper;

    @Override
    public Uni<List<TenantLobbyRequestModel>> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId,
            final Long tenantDeploymentId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, deployment_id, created, modified, lobby_id, deleted
                        from $schema.tab_tenant_lobby_request
                        where tenant_id = $1 and deployment_id = $2 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId, tenantDeploymentId),
                "Tenant lobby request",
                tenantLobbyRequestModelMapper::fromRow);
    }
}
