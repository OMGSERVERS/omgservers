package com.omgservers.service.module.tenant.impl.operation.selectActiveVersionProjectionsByTenantId;

import com.omgservers.model.version.VersionProjectionModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionProjectionModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
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
class SelectActiveVersionProjectionsByTenantIdOperationImpl
        implements SelectActiveVersionProjectionsByTenantIdOperation {

    final SelectListOperation selectListOperation;

    final VersionProjectionModelMapper versionProjectionModelMapper;

    @Override
    public Uni<List<VersionProjectionModel>> selectActiveVersionProjectionsByTenantId(final SqlConnection sqlConnection,
                                                                                      final int shard,
                                                                                      final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, stage_id, created, modified, deleted
                        from $schema.tab_tenant_version
                        where tenant_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(tenantId),
                "Version projection",
                versionProjectionModelMapper::fromRow);
    }
}
