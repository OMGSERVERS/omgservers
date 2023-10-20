package com.omgservers.module.tenant.impl.operation.selectVersionRuntimesIds;

import com.omgservers.module.tenant.impl.mapper.VersionRuntimeMapper;
import com.omgservers.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectVersionRuntimeIdsOperationImpl implements SelectVersionRuntimeIdsOperation {

    final SelectListOperation selectListOperation;

    final VersionRuntimeMapper versionRuntimeMapper;

    @Override
    public Uni<List<Long>> selectVersionRuntimeIds(final SqlConnection sqlConnection,
                                                   final int shard,
                                                   final Long tenantId,
                                                   final Long versionId,
                                                   final Boolean deleted) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_tenant_version_runtime
                        where tenant_id = $1 and version_id = $2 and deleted = $3
                        """,
                Arrays.asList(tenantId, versionId, deleted),
                "Version runtime id",
                row -> row.getLong("id"));
    }
}
