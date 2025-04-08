package com.omgservers.service.shard.tenant.impl.operation.tenantVersion;

import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.service.shard.tenant.impl.mapper.TenantVersionModelMapper;
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
class SelectTenantVersionOperationImpl implements SelectTenantVersionOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantVersionModelMapper tenantVersionModelMapper;

    @Override
    public Uni<TenantVersionModel> execute(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long tenantId,
                                           final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, project_id, created, modified, config, deleted
                        from $schema.tab_tenant_version
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(tenantId, id),
                "Tenant version",
                tenantVersionModelMapper::execute);
    }
}
