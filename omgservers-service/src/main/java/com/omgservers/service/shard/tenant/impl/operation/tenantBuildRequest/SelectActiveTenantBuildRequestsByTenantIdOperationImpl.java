package com.omgservers.service.shard.tenant.impl.operation.tenantBuildRequest;

import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestModel;
import com.omgservers.service.shard.tenant.impl.mapper.TenantBuildRequestModelMapper;
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
class SelectActiveTenantBuildRequestsByTenantIdOperationImpl
        implements SelectActiveTenantBuildRequestsByTenantIdOperation {

    final SelectListOperation selectListOperation;

    final TenantBuildRequestModelMapper tenantBuildRequestModelMapper;

    @Override
    public Uni<List<TenantBuildRequestModel>> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, build_number, 
                            deleted
                        from $schema.tab_tenant_build_request
                        where tenant_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId),
                "Tenant build request",
                tenantBuildRequestModelMapper::fromRow);
    }
}
