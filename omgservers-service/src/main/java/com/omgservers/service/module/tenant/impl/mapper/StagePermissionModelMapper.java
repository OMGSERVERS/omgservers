package com.omgservers.service.module.tenant.impl.mapper;

import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.model.stagePermission.StagePermissionModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class StagePermissionModelMapper {

    public StagePermissionModel fromRow(final Row row) {
        final var stagePermission = new StagePermissionModel();
        stagePermission.setId(row.getLong("id"));
        stagePermission.setIdempotencyKey(row.getString("idempotency_key"));
        stagePermission.setTenantId(row.getLong("tenant_id"));
        stagePermission.setStageId(row.getLong("stage_id"));
        stagePermission.setCreated(row.getOffsetDateTime("created").toInstant());
        stagePermission.setModified(row.getOffsetDateTime("modified").toInstant());
        stagePermission.setUserId(row.getLong("user_id"));
        stagePermission.setPermission(StagePermissionEnum.valueOf(row.getString("permission")));
        stagePermission.setDeleted(row.getBoolean("deleted"));
        return stagePermission;
    }
}
