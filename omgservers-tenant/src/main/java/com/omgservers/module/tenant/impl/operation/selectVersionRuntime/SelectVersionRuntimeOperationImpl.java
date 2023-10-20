package com.omgservers.module.tenant.impl.operation.selectVersionRuntime;

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
class SelectVersionRuntimeOperationImpl implements SelectVersionRuntimeOperation {

    final SelectObjectOperation selectObjectOperation;

    final VersionRuntimeMapper versionRuntimeMapper;

    @Override
    public Uni<VersionRuntimeModel> selectVersionRuntime(final SqlConnection sqlConnection,
                                                         final int shard,
                                                         final Long tenantId,
                                                         final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, tenant_id, version_id, created, modified, runtime_id, deleted
                        from $schema.tab_tenant_version_runtime
                        where tenant_id = $1 and id = $3
                        limit 1
                        """,
                Arrays.asList(tenantId, id),
                "Version runtime",
                versionRuntimeMapper::fromRow);
    }
}
