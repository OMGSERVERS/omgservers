package com.omgservers.platform.integrationtest;

import com.omgservers.application.module.tenantModule.model.project.ProjectPermissionEnum;
import com.omgservers.application.module.tenantModule.model.stage.StagePermissionEnum;
import com.omgservers.platforms.integrationtest.cli.AdminCli;
import com.omgservers.platforms.integrationtest.cli.DeveloperCli;
import com.omgservers.platforms.integrationtest.cli.TenantCli;
import com.omgservers.platforms.integrationtest.operations.bootstrapEnvironmentOperation.BootstrapEnvironmentOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@QuarkusTest
public class DeveloperApiCreateProjectTest extends Assertions {

    @Inject
    BootstrapEnvironmentOperation bootstrapEnvironmentOperation;

    @Inject
    DeveloperCli developerCli;

    @Inject
    AdminCli adminCli;

    @Inject
    TenantCli tenantCli;

    @Test
    void givenDeveloper_whenCreateProject_thenProjectAndStageCreatedWithPermissions() throws InterruptedException {
        bootstrapEnvironmentOperation.bootstrap();
        adminCli.createClient();
        developerCli.createClient();
        tenantCli.createClient();

        final var tenantId = adminCli.createTenant(tenantTitle());
        final var createNewDeveloperAdminResponse = adminCli.createDeveloper(tenantId);
        final var userId = createNewDeveloperAdminResponse.getUserId();
        final var password = createNewDeveloperAdminResponse.getPassword();
        developerCli.createToken(userId, password);

        final var createProjectDeveloperResponse = developerCli.createProject(tenantId);
        final var projectId = createProjectDeveloperResponse.getProjectId();
        final var stageId = createProjectDeveloperResponse.getStageId();
        assertNotNull(projectId);
        assertNotNull(stageId);

        Thread.sleep(5000);

        assertTrue(tenantCli.hasProjectPermission(tenantId, projectId, userId, ProjectPermissionEnum.CREATE_STAGE));
        assertTrue(tenantCli.hasStagePermission(tenantId, stageId, userId, StagePermissionEnum.CREATE_VERSION));
    }

    String tenantTitle() {
        return "tenant-" + UUID.randomUUID();
    }
}
