package com.omgservers.platform.integrationtest;

import com.omgservers.application.module.versionModule.model.VersionFileModel;
import com.omgservers.application.module.versionModule.model.VersionSourceCodeModel;
import com.omgservers.application.module.versionModule.model.VersionStatusEnum;
import com.omgservers.application.module.versionModule.model.VersionStageConfigModel;
import com.omgservers.platforms.integrationtest.cli.AdminCli;
import com.omgservers.platforms.integrationtest.cli.DeveloperCli;
import com.omgservers.platforms.integrationtest.operations.bootstrapEnvironmentOperation.BootstrapEnvironmentOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@QuarkusTest
public class DeveloperApiCreateVersionTest extends Assertions {

    @Inject
    BootstrapEnvironmentOperation bootstrapEnvironmentOperation;

    @Inject
    DeveloperCli developerCli;

    @Inject
    AdminCli adminCli;

    @Test
    void givenDeveloper_whenCreateVersion_thenCreated() throws Exception {
        bootstrapEnvironmentOperation.bootstrap();
        adminCli.createClient();
        developerCli.createClient();

        final var tenantUuid = adminCli.createTenant(tenantTitle());
        final var createNewDeveloperAdminResponse = adminCli.createDeveloper(tenantUuid);
        final var userId = createNewDeveloperAdminResponse.getUserId();
        final var password = createNewDeveloperAdminResponse.getPassword();
        developerCli.createToken(userId, password);

        final var createProjectDeveloperResponse = developerCli.createProject(tenantUuid, projectTitle());
        final var stageId = createProjectDeveloperResponse.getStageId();

        Thread.sleep(5000);

        final var stageConfig = VersionStageConfigModel.create();
        final var sourceCode = VersionSourceCodeModel.create();
        sourceCode.getFiles().add(new VersionFileModel("main.lua", Base64.getEncoder().encodeToString("""
                print('hello, world')
                """.getBytes(StandardCharsets.UTF_8))));
        final var version = developerCli.createVersion(tenantUuid, stageId, stageConfig, sourceCode);
        Thread.sleep(5000);
        assertEquals(VersionStatusEnum.DEPLOYED, developerCli.getVersionStatus(version));
    }

    String tenantTitle() {
        return "tenant-" + UUID.randomUUID();
    }

    String projectTitle() {
        return "project-" + UUID.randomUUID();
    }
}
