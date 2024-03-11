package com.omgservers.service.module.tenant.impl.operation.selectVersionMatchmakerRef;

import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionMatchmakerRefModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectVersionMatchmakerRefOperationImpl implements SelectVersionMatchmakerRefOperation {

    final SelectObjectOperation selectObjectOperation;

    final VersionMatchmakerRefModelMapper versionMatchmakerRefModelMapper;

    @Override
    public Uni<VersionMatchmakerRefModel> selectVersionMatchmakerRef(final SqlConnection sqlConnection,
                                                                     final int shard,
                                                                     final Long tenantId,
                                                                     final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, version_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_tenant_version_matchmaker_ref
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                Arrays.asList(tenantId, id),
                "Version matchmaker ref",
                versionMatchmakerRefModelMapper::fromRow);
    }
}
