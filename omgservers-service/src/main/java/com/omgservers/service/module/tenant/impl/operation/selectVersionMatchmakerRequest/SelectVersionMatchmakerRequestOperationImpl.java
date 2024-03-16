package com.omgservers.service.module.tenant.impl.operation.selectVersionMatchmakerRequest;

import com.omgservers.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionMatchmakerRequestModelMapper;
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
class SelectVersionMatchmakerRequestOperationImpl implements SelectVersionMatchmakerRequestOperation {

    final SelectObjectOperation selectObjectOperation;

    final VersionMatchmakerRequestModelMapper versionMatchmakerRequestModelMapper;

    @Override
    public Uni<VersionMatchmakerRequestModel> selectVersionMatchmakerRequest(final SqlConnection sqlConnection,
                                                                             final int shard,
                                                                             final Long tenantId,
                                                                             final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, version_id, created, modified, matchmaker_id, deleted
                        from $schema.tab_tenant_version_matchmaker_request
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(tenantId, id),
                "Version matchmaker request",
                versionMatchmakerRequestModelMapper::fromRow);
    }
}
