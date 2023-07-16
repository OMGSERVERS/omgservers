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

        final var tenantUuid = adminCli.createTenant(tenantTitle());
        final var createNewDeveloperAdminResponse = adminCli.createDeveloper(tenantUuid);
        final var user = createNewDeveloperAdminResponse.getUser();
        final var password = createNewDeveloperAdminResponse.getPassword();
        developerCli.createToken(user, password);

        final var createProjectDeveloperResponse = developerCli.createProject(tenantUuid, projectTitle());
        final var project = createProjectDeveloperResponse.getProject();
        final var stage = createProjectDeveloperResponse.getStage();
        assertNotNull(project);
        assertNotNull(stage);

        Thread.sleep(5000);

        assertTrue(tenantCli.hasProjectPermission(tenantUuid, project, user, ProjectPermissionEnum.CREATE_STAGE));
        assertTrue(tenantCli.hasStagePermission(tenantUuid, stage, user, StagePermissionEnum.CREATE_VERSION));
    }

    String tenantTitle() {
        return "tenant-" + UUID.randomUUID();
    }

    String projectTitle() {
        return "project-" + UUID.randomUUID();
    }
}
