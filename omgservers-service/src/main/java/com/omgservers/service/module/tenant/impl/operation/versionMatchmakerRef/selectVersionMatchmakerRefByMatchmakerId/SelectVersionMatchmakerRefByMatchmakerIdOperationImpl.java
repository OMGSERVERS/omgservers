package com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRef.selectVersionMatchmakerRefByMatchmakerId;

import com.omgservers.schema.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionMatchmakerRefModelMapper;
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
class SelectVersionMatchmakerRefByMatchmakerIdOperationImpl
        implements SelectVersionMatchmakerRefByMatchmakerIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final VersionMatchmakerRefModelMapper versionMatchmakerRefModelMapper;

    @Override
    public Uni<VersionMatchmakerRefModel> selectVersionMatchmakerRefByMatchmakerId(final SqlConnection sqlConnection,
                                                                                   final int shard,
                                                                                   final Long tenantId,
                                                                                   final Long versionId,
                                                                                   final Long matchmakerId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, version_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_tenant_version_matchmaker_ref
                        where tenant_id = $1 and version_id = $2 and matchmaker_id = $3
                        order by id desc
                        limit 1
                        """,
                List.of(
                        tenantId,
                        versionId,
                        matchmakerId
                ),
                "Version matchmaker ref",
                versionMatchmakerRefModelMapper::fromRow);
    }
}
