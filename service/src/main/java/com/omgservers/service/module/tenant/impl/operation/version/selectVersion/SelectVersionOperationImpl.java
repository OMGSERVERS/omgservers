package com.omgservers.service.module.tenant.impl.operation.version.selectVersion;

import com.omgservers.model.version.VersionModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectVersionOperationImpl implements SelectVersionOperation {

    final SelectObjectOperation selectObjectOperation;

    final VersionModelMapper versionModelMapper;

    @Override
    public Uni<VersionModel> selectVersion(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long tenantId,
                                           final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, stage_id, created, modified, config, archive, deleted
                        from $schema.tab_tenant_version
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(tenantId, id),
                "Version",
                versionModelMapper::fromRow);
    }
}
