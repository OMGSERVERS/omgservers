package com.omgservers.service.module.tenant.impl.operation.versionJenkinsRequest.selectActiveVersionJenkinsRequestsByTenantId;

import com.omgservers.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionJenkinsRequestModelMapper;
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
class SelectActiveVersionJenkinsRequestsByTenantIdImpl implements SelectActiveVersionJenkinsRequestsByTenantId {

    final SelectListOperation selectListOperation;

    final VersionJenkinsRequestModelMapper versionJenkinsRequestModelMapper;

    @Override
    public Uni<List<VersionJenkinsRequestModel>> selectActiveVersionJenkinsRequestsByTenantId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, build_number, 
                            deleted
                        from $schema.tab_tenant_version_jenkins_request
                        where tenant_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId),
                "Version jenkins request",
                versionJenkinsRequestModelMapper::fromRow);
    }
}
