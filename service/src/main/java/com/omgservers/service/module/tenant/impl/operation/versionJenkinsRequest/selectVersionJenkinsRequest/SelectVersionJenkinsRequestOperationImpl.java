package com.omgservers.service.module.tenant.impl.operation.versionJenkinsRequest.selectVersionJenkinsRequest;

import com.omgservers.schema.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionJenkinsRequestModelMapper;
import com.omgservers.service.server.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectVersionJenkinsRequestOperationImpl implements SelectVersionJenkinsRequestOperation {

    final SelectObjectOperation selectObjectOperation;

    final VersionJenkinsRequestModelMapper versionJenkinsRequestModelMapper;

    @Override
    public Uni<VersionJenkinsRequestModel> selectVersionJenkinsRequest(final SqlConnection sqlConnection,
                                                                       final int shard,
                                                                       final Long tenantId,
                                                                       final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, build_number, 
                            deleted
                        from $schema.tab_tenant_version_jenkins_request
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(tenantId, id),
                "Version jenkins request",
                versionJenkinsRequestModelMapper::fromRow);
    }
}
