package com.omgservers.service.module.tenant.impl.operation.tenantImage;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.service.module.tenant.impl.mapper.TenantImageModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveTenantImageByTenantVersionIdOperationImpl
        implements SelectActiveTenantImageByTenantVersionIdOperation {

    final SelectListOperation selectListOperation;

    final TenantImageModelMapper tenantImageModelMapper;

    @Override
    public Uni<List<TenantImageModel>> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId,
            final Long tenantVersionId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, image_id, deleted
                        from $schema.tab_tenant_image
                        where tenant_id = $1 and version_id = $2 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId, tenantVersionId),
                "Tenant image",
                tenantImageModelMapper::fromRow);
    }
}
