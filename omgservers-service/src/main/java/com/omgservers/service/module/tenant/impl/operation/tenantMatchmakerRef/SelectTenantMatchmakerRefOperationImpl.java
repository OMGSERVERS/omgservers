package com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRef;

import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.service.module.tenant.impl.mapper.TenantMatchmakerRefModelMapper;
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
class SelectTenantMatchmakerRefOperationImpl implements SelectTenantMatchmakerRefOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantMatchmakerRefModelMapper tenantMatchmakerRefModelMapper;

    @Override
    public Uni<TenantMatchmakerRefModel> execute(final SqlConnection sqlConnection,
                                                 final int shard,
                                                 final Long tenantId,
                                                 final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, deployment_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_tenant_matchmaker_ref
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(tenantId, id),
                "Tenant matchmaker ref",
                tenantMatchmakerRefModelMapper::fromRow);
    }
}
