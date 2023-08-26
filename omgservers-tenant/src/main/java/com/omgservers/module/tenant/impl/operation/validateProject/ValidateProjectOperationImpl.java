package com.omgservers.module.tenant.impl.operation.validateProject;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.project.ProjectModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ApplicationScoped
class ValidateProjectOperationImpl implements ValidateProjectOperation {

    @Override
    public ProjectModel validateProject(ProjectModel project) {
        if (project == null) {
            throw new IllegalArgumentException("project is null");
        }

        var config = project.getConfig();

        Map<String, Boolean> results = new HashMap<>();

        // TODO: validate project

        var valid = results.values().stream().allMatch(Boolean.TRUE::equals);
        if (valid) {
            log.info("Project is valid, project={}", project);
            return project;
        } else {
            throw new ServerSideBadRequestException(String.format("bad project, project=%s", project));
        }
    }
}
