package com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerResource;

import com.omgservers.schema.model.tenantMatchmakerResource.TenantMatchmakerResourceModel;
import com.omgservers.service.operation.server.SelectListOperation;
import com.omgservers.service.shard.tenant.impl.mapper.TenantMatchmakerResourceModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveTenantMatchmakerResourcesByTenantIdOperationImpl
        implements SelectActiveTenantMatchmakerResourcesByTenantIdOperation {

    final SelectListOperation selectListOperation;

    final TenantMatchmakerResourceModelMapper tenantMatchmakerResourceModelMapper;

    @Override
    public Uni<List<TenantMatchmakerResourceModel>> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, deployment_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_tenant_matchmaker_resource
                        where tenant_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId),
                "Matchmaker resource",
                tenantMatchmakerResourceModelMapper::fromRow);
    }
}
