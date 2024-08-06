package com.omgservers.service.module.tenant.impl.operation.versionImageRef.selectActiveVersionImageRefsByTenantId;

import com.omgservers.schema.model.versionImageRef.VersionImageRefModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionImageRefModelMapper;
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
class SelectActiveVersionImageRefsByTenantIdImpl implements SelectActiveVersionImageRefsByTenantId {

    final SelectListOperation selectListOperation;

    final VersionImageRefModelMapper versionImageRefModelMapper;

    @Override
    public Uni<List<VersionImageRefModel>> selectActiveVersionImageRefsByTenantId(final SqlConnection sqlConnection,
                                                                                  final int shard,
                                                                                  final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, image_id, deleted
                        from $schema.tab_tenant_version_image_ref
                        where tenant_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId),
                "Version image ref",
                versionImageRefModelMapper::fromRow);
    }
}
