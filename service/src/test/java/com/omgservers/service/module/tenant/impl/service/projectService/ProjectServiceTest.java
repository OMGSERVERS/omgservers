package com.omgservers.service.module.tenant.impl.service.projectService;

import com.omgservers.service.module.tenant.impl.service.projectService.ProjectService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
@QuarkusTest
class ProjectServiceTest extends Assertions {

    @Inject
    ProjectService projectService;
}