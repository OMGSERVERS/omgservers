package com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRequest;

import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import com.omgservers.service.module.tenant.impl.mapper.TenantMatchmakerRequestModelMapper;
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
class SelectTenantMatchmakerRequestByMatchmakerIdOperationImpl
        implements SelectTenantMatchmakerRequestByMatchmakerIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantMatchmakerRequestModelMapper tenantMatchmakerRequestModelMapper;

    @Override
    public Uni<TenantMatchmakerRequestModel> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId,
            final Long deploymentId,
            final Long matchmakerId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, deployment_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_tenant_matchmaker_request
                        where tenant_id = $1 and deployment_id = $2 and matchmaker_id = $3
                        order by id desc
                        limit 1
                        """,
                List.of(
                        tenantId,
                        deploymentId,
                        matchmakerId
                ),
                "Tenant matchmaker version",
                tenantMatchmakerRequestModelMapper::fromRow);
    }
}
