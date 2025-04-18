package com.omgservers.service.shard.tenant.impl.operation.tenantImage;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.service.shard.tenant.impl.mapper.TenantImageModelMapper;
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
class SelectTenantImageOperationImpl implements SelectTenantImageOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantImageModelMapper tenantImageModelMapper;

    @Override
    public Uni<TenantImageModel> execute(final SqlConnection sqlConnection,
                                         final int slot,
                                         final Long tenantId,
                                         final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, image_id, config,
                            deleted
                        from $slot.tab_tenant_image
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(tenantId, id),
                "Tenant image",
                tenantImageModelMapper::execute);
    }
}
