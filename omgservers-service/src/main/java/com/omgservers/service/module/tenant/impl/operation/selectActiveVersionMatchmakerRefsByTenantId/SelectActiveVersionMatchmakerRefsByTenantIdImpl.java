package com.omgservers.service.module.tenant.impl.operation.selectActiveVersionMatchmakerRefsByTenantId;

import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionMatchmakerRefModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveVersionMatchmakerRefsByTenantIdImpl implements SelectActiveVersionMatchmakerRefsByTenantId {

    final SelectListOperation selectListOperation;

    final VersionMatchmakerRefModelMapper versionMatchmakerRefModelMapper;

    @Override
    public Uni<List<VersionMatchmakerRefModel>> selectActiveVersionMatchmakerRefsByTenantId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, version_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_tenant_version_matchmaker_ref
                        where tenant_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId),
                "Version matchmaker ref",
                versionMatchmakerRefModelMapper::fromRow);
    }
}
