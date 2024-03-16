package com.omgservers.service.module.tenant.impl.operation.selectVersionIdByStageId;

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
class SelectVersionIdByStageIdOperationImpl implements SelectVersionIdByStageIdOperation {

    final SelectObjectOperation selectObjectOperation;

    @Override
    public Uni<Long> selectVersionIdByStageId(final SqlConnection sqlConnection,
                                              final int shard,
                                              final Long tenantId,
                                              final Long stageId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_tenant_version
                        where tenant_id = $1 and stage_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(tenantId, stageId),
                "Version",
                row -> row.getLong("id"));
    }
}
