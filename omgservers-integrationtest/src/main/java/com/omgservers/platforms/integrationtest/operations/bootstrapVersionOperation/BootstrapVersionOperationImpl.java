package com.omgservers.platforms.integrationtest.operations.bootstrapVersionOperation;

import com.omgservers.model.version.VersionFileModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.platforms.integrationtest.cli.AdminCli;
import com.omgservers.platforms.integrationtest.cli.DeveloperCli;
import com.omgservers.platforms.integrationtest.operations.bootstrapEnvironmentOperation.BootstrapEnvironmentOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BootstrapVersionOperationImpl implements BootstrapVersionOperation {

    final BootstrapEnvironmentOperation bootstrapEnvironmentOperation;
    final DeveloperCli developerCli;
    final AdminCli adminCli;

    @Override
    public VersionParameters bootstrap(String script) throws InterruptedException {
        return bootstrap(script, VersionStageConfigModel.create());
    }

    @Override
    public VersionParameters bootstrap(String script, VersionStageConfigModel stageConfig) throws InterruptedException {
        bootstrapEnvironmentOperation.bootstrap();
        adminCli.createClient();
        developerCli.createClient();

        final var tenantId = adminCli.createTenant(tenantTitle());
        final var createDeveloperResponse = adminCli.createDeveloper(tenantId);
        final var developerUser = createDeveloperResponse.getUserId();
        final var developerPassword = createDeveloperResponse.getPassword();
        developerCli.createToken(developerUser, developerPassword);

        final var createProjectResponse = developerCli.createProject(tenantId);
        final var projectId = createProjectResponse.getProjectId();
        final var stageId = createProjectResponse.getStageId();
        final var stageSecret = createProjectResponse.getSecret();

        Thread.sleep(5000);

        final var sourceCode = VersionSourceCodeModel.create();
        sourceCode.getFiles().add(new VersionFileModel("main.lua", Base64.getEncoder()
                .encodeToString(script.getBytes(StandardCharsets.UTF_8))));
        final var version = developerCli.createVersion(tenantId, stageId, stageConfig, sourceCode);

        Thread.sleep(2000);

        return VersionParameters.builder()
                .tenantId(tenantId)
                .developerUser(developerUser)
                .developerPassword(developerPassword)
                .projectId(projectId)
                .stageId(stageId)
                .stageSecret(stageSecret)
                .versionId(version)
                .build();
    }

    String tenantTitle() {
        return "tenant-" + UUID.randomUUID();
    }
}
