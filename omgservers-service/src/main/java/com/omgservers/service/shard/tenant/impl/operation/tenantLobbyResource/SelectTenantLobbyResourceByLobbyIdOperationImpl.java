package com.omgservers.service.shard.tenant.impl.operation.tenantLobbyResource;

import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
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
class SelectTenantLobbyResourceByLobbyIdOperationImpl implements SelectTenantLobbyResourceByLobbyIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantLobbyResourceModelMapper tenantLobbyResourceModelMapper;

    @Override
    public Uni<TenantLobbyResourceModel> execute(final SqlConnection sqlConnection,
                                                 final int shard,
                                                 final Long tenantId,
                                                 final Long tenantDeploymentId,
                                                 final Long lobbyId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, deployment_id, created, modified, lobby_id, deleted
                        from $schema.tab_tenant_lobby_resource
                        where tenant_id = $1 and deployment_id = $2 and lobby_id = $3
                        order by id desc
                        limit 1
                        """,
                List.of(tenantId, tenantDeploymentId, lobbyId),
                "Lobby resource",
                tenantLobbyResourceModelMapper::fromRow);
    }
}
