package com.omgservers.service.module.tenant.impl.operation.versionImageRef.selectActiveVersionImageRefsByVersionId;

import com.omgservers.model.versionImageRef.VersionImageRefModel;
import com.omgservers.service.module.tenant.impl.mapper.VersionImageRefModelMapper;
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
class SelectActiveVersionImageRefsByVersionIdImpl implements SelectActiveVersionImageRefsByVersionId {

    final SelectListOperation selectListOperation;

    final VersionImageRefModelMapper versionImageRefModelMapper;

    @Override
    public Uni<List<VersionImageRefModel>> selectActiveVersionImageRefsByVersionId(final SqlConnection sqlConnection,
                                                                                   final int shard,
                                                                                   final Long tenantId,
                                                                                   final Long versionId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, image_id, deleted
                        from $schema.tab_tenant_version_image_ref
                        where tenant_id = $1 and version_id = $2 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId, versionId),
                "Version image ref",
                versionImageRefModelMapper::fromRow);
    }
}
