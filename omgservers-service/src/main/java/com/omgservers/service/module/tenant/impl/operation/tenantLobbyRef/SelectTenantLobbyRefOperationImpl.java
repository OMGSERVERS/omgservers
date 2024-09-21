package com.omgservers.service.module.tenant.impl.operation.tenantLobbyRef;

import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.service.module.tenant.impl.mapper.TenantLobbyRefModelMapper;
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
class SelectTenantLobbyRefOperationImpl implements SelectTenantLobbyRefOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantLobbyRefModelMapper tenantLobbyRefModelMapper;

    @Override
    public Uni<TenantLobbyRefModel> execute(final SqlConnection sqlConnection,
                                            final int shard,
                                            final Long tenantId,
                                            final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, deployment_id, created, modified, lobby_id, deleted
                        from $schema.tab_tenant_lobby_ref
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(tenantId, id),
                "Tenant lobby ref",
                tenantLobbyRefModelMapper::fromRow);
    }
}
