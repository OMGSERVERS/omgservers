package com.omgservers.service.module.tenant.impl.operation.selectActiveVersionRuntimesByTenantId;

import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionRuntimeModelMapper;
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
class SelectActiveVersionRuntimesByTenantIdImpl implements SelectActiveVersionRuntimesByTenantId {

    final SelectListOperation selectListOperation;

    final VersionRuntimeModelMapper versionRuntimeModelMapper;

    @Override
    public Uni<List<VersionRuntimeModel>> selectActiveVersionRuntimesByTenantId(final SqlConnection sqlConnection,
                                                                                final int shard,
                                                                                final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, tenant_id, version_id, created, modified, runtime_id, deleted
                        from $schema.tab_tenant_version_runtime
                        where tenant_id = $1 and deleted = false
                        """,
                Collections.singletonList(tenantId),
                "Version runtime",
                versionRuntimeModelMapper::fromRow);
    }
}
