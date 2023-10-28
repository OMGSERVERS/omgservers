package com.omgservers.module.tenant.factory;

import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ProjectModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ProjectModel create(Long tenantId, ProjectConfigModel config) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, config);
    }

    public ProjectModel create(Long id, Long tenantId, ProjectConfigModel config) {
        var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        ProjectModel project = new ProjectModel();
        project.setId(id);
        project.setTenantId(tenantId);
        project.setCreated(now);
        project.setModified(now);
        project.setConfig(config);
        return project;
    }
}
