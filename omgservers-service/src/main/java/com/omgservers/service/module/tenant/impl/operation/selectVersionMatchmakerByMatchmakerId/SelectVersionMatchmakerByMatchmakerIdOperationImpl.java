package com.omgservers.service.module.tenant.impl.operation.selectVersionMatchmakerByMatchmakerId;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionMatchmakerModelMapper;
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
class SelectVersionMatchmakerByMatchmakerIdOperationImpl implements SelectVersionMatchmakerByMatchmakerIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final VersionMatchmakerModelMapper versionMatchmakerModelMapper;

    @Override
    public Uni<VersionMatchmakerModel> selectVersionMatchmakerByMatchmakerId(final SqlConnection sqlConnection,
                                                                             final int shard,
                                                                             final Long tenantId,
                                                                             final Long matchmakerId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, tenant_id, version_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_tenant_version_matchmaker
                        where tenant_id = $1 and matchmaker_id = $2
                        limit 1
                        """,
                Arrays.asList(tenantId, matchmakerId),
                "Version matchmaker",
                versionMatchmakerModelMapper::fromRow);
    }
}
