package com.omgservers.service.module.tenant.impl.operation.selectActiveVersionMatchmakersByTenantId;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionMatchmakerModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveVersionMatchmakersByTenantIdImpl implements SelectActiveVersionMatchmakersByTenantId {

    final SelectListOperation selectListOperation;

    final VersionMatchmakerModelMapper versionMatchmakerModelMapper;

    @Override
    public Uni<List<VersionMatchmakerModel>> selectActiveVersionMatchmakersTenantId(final SqlConnection sqlConnection,
                                                                                    final int shard,
                                                                                    final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, tenant_id, version_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_tenant_version_matchmaker
                        where tenant_id = $1 and deleted = false
                        """,
                Collections.singletonList(tenantId),
                "Version matchmaker",
                versionMatchmakerModelMapper::fromRow);
    }
}
