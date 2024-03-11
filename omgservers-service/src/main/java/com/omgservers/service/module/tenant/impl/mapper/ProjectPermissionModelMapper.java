package com.omgservers.service.module.tenant.impl.mapper;

import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.model.projectPermission.ProjectPermissionModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ProjectPermissionModelMapper {

    public ProjectPermissionModel fromRow(Row row) {
        final var projectPermission = new ProjectPermissionModel();
        projectPermission.setId(row.getLong("id"));
        projectPermission.setTenantId(row.getLong("tenant_id"));
        projectPermission.setProjectId(row.getLong("project_id"));
        projectPermission.setCreated(row.getOffsetDateTime("created").toInstant());
        projectPermission.setModified(row.getOffsetDateTime("modified").toInstant());
        projectPermission.setIdempotencyKey(row.getString("idempotency_key"));
        projectPermission.setUserId(row.getLong("user_id"));
        projectPermission.setPermission(ProjectPermissionEnum.valueOf(row.getString("permission")));
        projectPermission.setDeleted(row.getBoolean("deleted"));
        return projectPermission;
    }
}
