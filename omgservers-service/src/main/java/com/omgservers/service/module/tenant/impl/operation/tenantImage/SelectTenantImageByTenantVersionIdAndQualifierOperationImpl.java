package com.omgservers.service.module.tenant.impl.operation.tenantImage;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.service.module.tenant.impl.mapper.TenantImageModelMapper;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectTenantImageByTenantVersionIdAndQualifierOperationImpl
        implements SelectTenantImageByTenantVersionIdAndQualifierOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantImageModelMapper tenantImageModelMapper;

    @Override
    public Uni<TenantImageModel> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId,
            final Long tenantVersionId,
            final TenantImageQualifierEnum qualifier) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, image_id, deleted
                        from $schema.tab_tenant_image
                        where tenant_id = $1 and version_id = $2 and qualifier = $3 and deleted = false
                        order by id desc
                        limit 1
                        """,
                List.of(tenantId, tenantVersionId, qualifier),
                "Tenant image",
                tenantImageModelMapper::fromRow);
    }
}
