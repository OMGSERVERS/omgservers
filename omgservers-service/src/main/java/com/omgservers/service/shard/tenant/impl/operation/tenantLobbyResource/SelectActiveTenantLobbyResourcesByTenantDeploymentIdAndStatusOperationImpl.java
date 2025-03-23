package com.omgservers.service.shard.tenant.impl.operation.tenantLobbyResource;

import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceStatusEnum;
import com.omgservers.service.operation.server.SelectListOperation;
import com.omgservers.service.shard.tenant.impl.mapper.TenantLobbyResourceModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveTenantLobbyResourcesByTenantDeploymentIdAndStatusOperationImpl
        implements SelectActiveTenantLobbyResourcesByTenantDeploymentIdAndStatusOperation {

    final SelectListOperation selectListOperation;

    final TenantLobbyResourceModelMapper tenantLobbyResourceModelMapper;

    @Override
    public Uni<List<TenantLobbyResourceModel>> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId,
            final Long tenantDeploymentId,
            final TenantLobbyResourceStatusEnum status) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, deployment_id, created, modified, lobby_id, status, deleted
                        from $schema.tab_tenant_lobby_resource
                        where tenant_id = $1 and deployment_id = $2 and status = $3 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId, tenantDeploymentId, status),
                "Lobby resource",
                tenantLobbyResourceModelMapper::fromRow);
    }
}
