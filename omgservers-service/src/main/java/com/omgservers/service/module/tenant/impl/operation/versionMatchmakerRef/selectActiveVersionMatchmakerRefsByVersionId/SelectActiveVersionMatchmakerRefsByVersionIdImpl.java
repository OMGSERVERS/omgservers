package com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRef.selectActiveVersionMatchmakerRefsByVersionId;

import com.omgservers.schema.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionMatchmakerRefModelMapper;
import com.omgservers.service.server.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveVersionMatchmakerRefsByVersionIdImpl implements SelectActiveVersionMatchmakerRefsByVersionId {

    final SelectListOperation selectListOperation;

    final VersionMatchmakerRefModelMapper versionMatchmakerRefModelMapper;

    @Override
    public Uni<List<VersionMatchmakerRefModel>> selectActiveVersionMatchmakerRefsByVersionId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId,
            final Long versionId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, version_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_tenant_version_matchmaker_ref
                        where tenant_id = $1 and version_id = $2 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId, versionId),
                "Version matchmaker ref",
                versionMatchmakerRefModelMapper::fromRow);
    }
}
