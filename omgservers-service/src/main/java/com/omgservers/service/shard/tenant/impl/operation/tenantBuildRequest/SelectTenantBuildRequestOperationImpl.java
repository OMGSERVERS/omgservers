package com.omgservers.service.shard.tenant.impl.operation.tenantBuildRequest;

import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestModel;
import com.omgservers.service.shard.tenant.impl.mapper.TenantBuildRequestModelMapper;
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
class SelectTenantBuildRequestOperationImpl implements SelectTenantBuildRequestOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantBuildRequestModelMapper tenantBuildRequestModelMapper;

    @Override
    public Uni<TenantBuildRequestModel> execute(final SqlConnection sqlConnection,
                                                final int shard,
                                                final Long tenantId,
                                                final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, build_number, 
                            deleted
                        from $schema.tab_tenant_build_request
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(tenantId, id),
                "Tenant build request",
                tenantBuildRequestModelMapper::fromRow);
    }
}
