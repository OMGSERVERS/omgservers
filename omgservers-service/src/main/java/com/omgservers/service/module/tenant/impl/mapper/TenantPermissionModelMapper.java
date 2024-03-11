package com.omgservers.service.module.tenant.impl.mapper;

import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.model.tenantPermission.TenantPermissionModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantPermissionModelMapper {

    public TenantPermissionModel fromRow(Row row) {
        final var tenantPermission = new TenantPermissionModel();
        tenantPermission.setId(row.getLong("id"));
        tenantPermission.setTenantId(row.getLong("tenant_id"));
        tenantPermission.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantPermission.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantPermission.setIdempotencyKey(row.getString("idempotency_key"));
        tenantPermission.setUserId(row.getLong("user_id"));
        tenantPermission.setPermission(TenantPermissionEnum.valueOf(row.getString("permission")));
        tenantPermission.setDeleted(row.getBoolean("deleted"));
        return tenantPermission;
    }
}
