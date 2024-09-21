package com.omgservers.service.module.tenant.impl.operation.tenantJenkinsRequest;

import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestModel;
import com.omgservers.service.module.tenant.impl.mapper.TenantJenkinsRequestModelMapper;
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
class SelectTenantJenkinsRequestOperationImpl implements SelectTenantJenkinsRequestOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantJenkinsRequestModelMapper tenantJenkinsRequestModelMapper;

    @Override
    public Uni<TenantJenkinsRequestModel> execute(final SqlConnection sqlConnection,
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
                        from $schema.tab_tenant_jenkins_request
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(tenantId, id),
                "Tenant jenkins request",
                tenantJenkinsRequestModelMapper::fromRow);
    }
}
