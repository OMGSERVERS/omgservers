package com.omgservers.service.shard.tenant.impl.operation.tenantVersion;

import com.omgservers.schema.model.tenantVersion.TenantVersionProjectionModel;
import com.omgservers.service.shard.tenant.impl.mapper.TenantVersionProjectionModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveTenantVersionProjectionsByTenantIdOperationImpl
        implements SelectActiveTenantVersionProjectionsByTenantIdOperation {

    final SelectListOperation selectListOperation;

    final TenantVersionProjectionModelMapper tenantVersionProjectionModelMapper;

    @Override
    public Uni<List<TenantVersionProjectionModel>> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, project_id, created, modified, deleted
                        from $schema.tab_tenant_version
                        where tenant_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(tenantId),
                "Tenant version projection",
                tenantVersionProjectionModelMapper::execute);
    }
}
