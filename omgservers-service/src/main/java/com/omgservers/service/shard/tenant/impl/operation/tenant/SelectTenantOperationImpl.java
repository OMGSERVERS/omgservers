package com.omgservers.service.shard.tenant.impl.operation.tenant;

import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.service.shard.tenant.impl.mapper.TenantModelMapper;
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
class SelectTenantOperationImpl implements SelectTenantOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantModelMapper tenantModelMapper;

    @Override
    public Uni<TenantModel> execute(final SqlConnection sqlConnection,
                                    final int shard,
                                    final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, created, modified, deleted
                        from $shard.tab_tenant
                        where id = $1
                        limit 1
                        """,
                List.of(id),
                "Tenant",
                tenantModelMapper::execute);
    }
}
