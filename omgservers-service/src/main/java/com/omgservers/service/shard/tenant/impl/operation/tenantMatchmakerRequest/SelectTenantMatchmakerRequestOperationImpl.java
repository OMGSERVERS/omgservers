package com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerRequest;

import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import com.omgservers.service.shard.tenant.impl.mapper.TenantMatchmakerRequestModelMapper;
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
class SelectTenantMatchmakerRequestOperationImpl implements SelectTenantMatchmakerRequestOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantMatchmakerRequestModelMapper tenantMatchmakerRequestModelMapper;

    @Override
    public Uni<TenantMatchmakerRequestModel> execute(final SqlConnection sqlConnection,
                                                     final int shard,
                                                     final Long tenantId,
                                                     final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, deployment_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_tenant_matchmaker_request
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(tenantId, id),
                "Matchmaker request",
                tenantMatchmakerRequestModelMapper::fromRow);
    }
}
