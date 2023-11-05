package com.omgservers.service.factory;

import com.omgservers.model.project.ProjectModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
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

    public ProjectModel create(Long tenantId) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId);
    }

    public ProjectModel create(Long id, Long tenantId) {
        var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        ProjectModel project = new ProjectModel();
        project.setId(id);
        project.setTenantId(tenantId);
        project.setCreated(now);
        project.setModified(now);
        project.setDeleted(false);
        return project;
    }
}
