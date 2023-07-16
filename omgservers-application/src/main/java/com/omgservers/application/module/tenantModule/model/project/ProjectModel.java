package com.omgservers.application.module.tenantModule.model.project;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectModel {

    static public ProjectModel create(UUID tenant, UUID owner, ProjectConfigModel config) {
        return create(tenant, UUID.randomUUID(), owner, config);
    }

    static public ProjectModel create(UUID tenant, UUID uuid, UUID owner, ProjectConfigModel config) {
        if (tenant == null) {
            throw new ServerSideBadRequestException("tenant is null");
        }
        if (uuid == null) {
            throw new ServerSideBadRequestException("uuid is null");
        }
        if (owner == null) {
            throw new ServerSideBadRequestException("owner is null");
        }
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }

        var now = Instant.now();

        ProjectModel project = new ProjectModel();
        project.setTenant(tenant);
        project.setCreated(now);
        project.setModified(now);
        project.setUuid(uuid);
        project.setOwner(owner);
        project.setConfig(config);
        return project;
    }

    UUID tenant;
    @ToString.Exclude
    Instant created;
    @ToString.Exclude
    Instant modified;
    UUID uuid;
    UUID owner;
    @ToString.Exclude
    ProjectConfigModel config;
}
