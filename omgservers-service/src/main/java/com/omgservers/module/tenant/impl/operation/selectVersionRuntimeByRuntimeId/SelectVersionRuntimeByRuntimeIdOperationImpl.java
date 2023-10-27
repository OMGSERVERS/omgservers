package com.omgservers.module.tenant.impl.operation.selectVersionRuntimeByRuntimeId;

import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.module.tenant.impl.mapper.VersionRuntimeMapper;
import com.omgservers.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectVersionRuntimeByRuntimeIdOperationImpl implements SelectVersionRuntimeByRuntimeIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final VersionRuntimeMapper versionRuntimeMapper;

    @Override
    public Uni<VersionRuntimeModel> selectVersionRuntimeByRuntimeId(final SqlConnection sqlConnection,
                                                                    final int shard,
                                                                    final Long tenantId,
                                                                    final Long versionId,
                                                                    final Long runtimeId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, tenant_id, version_id, created, modified, runtime_id, deleted
                        from $schema.tab_tenant_version_runtime
                        where tenant_id = $1 and version_id = $2 and runtime_id = $3
                        limit 1
                        """,
                Arrays.asList(tenantId, versionId, runtimeId),
                "Version runtime",
                versionRuntimeMapper::fromRow);
    }
}
