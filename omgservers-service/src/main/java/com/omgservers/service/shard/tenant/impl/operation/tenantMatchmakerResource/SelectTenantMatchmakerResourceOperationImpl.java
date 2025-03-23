package com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerResource;

import com.omgservers.schema.model.tenantMatchmakerResource.TenantMatchmakerResourceModel;
import com.omgservers.service.shard.tenant.impl.mapper.TenantMatchmakerResourceModelMapper;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectTenantMatchmakerResourceOperationImpl implements SelectTenantMatchmakerResourceOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantMatchmakerResourceModelMapper tenantMatchmakerResourceModelMapper;

    @Override
    public Uni<TenantMatchmakerResourceModel> execute(final SqlConnection sqlConnection,
                                                      final int shard,
                                                      final Long tenantId,
                                                      final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, deployment_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_tenant_matchmaker_resource
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(tenantId, id),
                "Matchmaker resource",
                tenantMatchmakerResourceModelMapper::fromRow);
    }
}
