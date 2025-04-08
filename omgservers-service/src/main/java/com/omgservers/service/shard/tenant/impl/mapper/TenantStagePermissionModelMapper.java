package com.omgservers.service.shard.tenant.impl.mapper;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantStagePermissionModelMapper {

    public TenantStagePermissionModel execute(final Row row) {
        final var tenantStagePermission = new TenantStagePermissionModel();
        tenantStagePermission.setId(row.getLong("id"));
        tenantStagePermission.setIdempotencyKey(row.getString("idempotency_key"));
        tenantStagePermission.setTenantId(row.getLong("tenant_id"));
        tenantStagePermission.setStageId(row.getLong("stage_id"));
        tenantStagePermission.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantStagePermission.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantStagePermission.setUserId(row.getLong("user_id"));
        tenantStagePermission.setPermission(TenantStagePermissionQualifierEnum.valueOf(row.getString("permission")));
        tenantStagePermission.setDeleted(row.getBoolean("deleted"));
        return tenantStagePermission;
    }
}
