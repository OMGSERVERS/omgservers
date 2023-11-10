package com.omgservers.service.module.tenant.impl.operation.selectActiveVersionMatchmakers;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionMatchmakerModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveVersionMatchmakersImpl implements SelectActiveVersionMatchmakers {

    final SelectListOperation selectListOperation;

    final VersionMatchmakerModelMapper versionMatchmakerModelMapper;

    @Override
    public Uni<List<VersionMatchmakerModel>> selectActiveVersionMatchmakers(final SqlConnection sqlConnection,
                                                                            final int shard,
                                                                            final Long tenantId,
                                                                            final Long versionId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, tenant_id, version_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_tenant_version_matchmaker
                        where tenant_id = $1 and version_id = $2 and deleted = false
                        """,
                Arrays.asList(tenantId, versionId),
                "Version matchmaker",
                versionMatchmakerModelMapper::fromRow);
    }
}
