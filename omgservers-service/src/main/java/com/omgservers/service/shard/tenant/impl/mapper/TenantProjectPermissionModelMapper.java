package com.omgservers.service.shard.tenant.impl.mapper;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantProjectPermissionModelMapper {

    public TenantProjectPermissionModel fromRow(final Row row) {
        final var tenantProjectPermission = new TenantProjectPermissionModel();
        tenantProjectPermission.setId(row.getLong("id"));
        tenantProjectPermission.setTenantId(row.getLong("tenant_id"));
        tenantProjectPermission.setProjectId(row.getLong("project_id"));
        tenantProjectPermission.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantProjectPermission.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantProjectPermission.setIdempotencyKey(row.getString("idempotency_key"));
        tenantProjectPermission.setUserId(row.getLong("user_id"));
        tenantProjectPermission.setPermission(TenantProjectPermissionQualifierEnum.valueOf(row.getString("permission")));
        tenantProjectPermission.setDeleted(row.getBoolean("deleted"));
        return tenantProjectPermission;
    }
}
