package com.omgservers.service.module.tenant.impl.operation.versionImageRef.selectVersionImageRefsByVersionIdAndQualifier;

import com.omgservers.model.versionImageRef.VersionImageRefModel;
import com.omgservers.model.versionImageRef.VersionImageRefQualifierEnum;
import com.omgservers.service.module.tenant.impl.mapper.VersionImageRefModelMapper;
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
class SelectVersionImageRefsByVersionIdAndQualifierImpl
        implements SelectVersionImageRefsByVersionIdAndQualifier {

    final SelectObjectOperation selectObjectOperation;

    final VersionImageRefModelMapper versionImageRefModelMapper;

    @Override
    public Uni<VersionImageRefModel> selectVersionImageRefsByVersionIdAndQualifier(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId,
            final Long versionId,
            final VersionImageRefQualifierEnum qualifier) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, image_id, deleted
                        from $schema.tab_tenant_version_image_ref
                        where tenant_id = $1 and version_id = $2 and qualifier = $3 and deleted = false
                        order by id desc
                        limit 1
                        """,
                List.of(tenantId, versionId, qualifier),
                "Version image ref",
                versionImageRefModelMapper::fromRow);
    }
}
