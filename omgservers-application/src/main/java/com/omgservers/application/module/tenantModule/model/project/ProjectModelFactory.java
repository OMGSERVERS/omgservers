package com.omgservers.application.module.tenantModule.model.project;

import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ProjectModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ProjectModel create(Long tenantId, Long ownerId, ProjectConfigModel config) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, ownerId, config);
    }

    public ProjectModel create(Long id, Long tenantId, Long ownerId, ProjectConfigModel config) {
        if (id == null) {
            throw new ServerSideBadRequestException("id is null");
        }
        if (tenantId == null) {
            throw new ServerSideBadRequestException("tenantId is null");
        }
        if (ownerId == null) {
            throw new ServerSideBadRequestException("ownerId is null");
        }
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }

        var now = Instant.now();

        ProjectModel project = new ProjectModel();
        project.setId(id);
        project.setTenantId(tenantId);
        project.setCreated(now);
        project.setModified(now);
        project.setOwnerId(ownerId);
        project.setConfig(config);
        return project;
    }
}