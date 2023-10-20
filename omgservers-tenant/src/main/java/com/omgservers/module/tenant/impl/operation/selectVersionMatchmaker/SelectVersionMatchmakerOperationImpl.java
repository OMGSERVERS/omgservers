package com.omgservers.module.tenant.impl.operation.selectVersionMatchmaker;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.module.tenant.impl.mapper.VersionMatchmakerMapper;
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
class SelectVersionMatchmakerOperationImpl implements SelectVersionMatchmakerOperation {

    final SelectObjectOperation selectObjectOperation;

    final VersionMatchmakerMapper versionMatchmakerMapper;

    @Override
    public Uni<VersionMatchmakerModel> selectVersionMatchmaker(final SqlConnection sqlConnection,
                                                               final int shard,
                                                               final Long tenantId,
                                                               final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, tenant_id, version_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_tenant_version_matchmaker
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                Arrays.asList(tenantId, id),
                "Version matchmaker",
                versionMatchmakerMapper::fromRow);
    }
}
