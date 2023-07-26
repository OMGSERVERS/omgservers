package com.omgservers.platforms.integrationtest.operations.createVersionAndSignUpOperation;

import com.omgservers.application.module.gatewayModule.model.message.body.CredentialsMessageBodyModel;
import com.omgservers.application.module.versionModule.model.VersionFileModel;
import com.omgservers.application.module.versionModule.model.VersionSourceCodeModel;
import com.omgservers.application.module.versionModule.model.VersionStageConfigModel;
import com.omgservers.platforms.integrationtest.cli.AdminCli;
import com.omgservers.platforms.integrationtest.cli.DeveloperCli;
import com.omgservers.platforms.integrationtest.operations.bootstrapEnvironmentOperation.BootstrapEnvironmentOperation;
import com.omgservers.platforms.integrationtest.testClient.TestClient;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateVersionAndSignUpOperationImpl implements CreateVersionAndSignUpOperation {

    final BootstrapEnvironmentOperation bootstrapEnvironmentOperation;
    final DeveloperCli developerCli;
    final AdminCli adminCli;
    final TestClient testClient;

    @Override
    public VersionParameters createVersionAndSignUp(String script) throws Exception {
        bootstrapEnvironmentOperation.bootstrap();
        adminCli.createClient();
        developerCli.createClient();

        final var tenantId = adminCli.createTenant(tenantTitle());
        final var createNewDeveloperAdminResponse = adminCli.createDeveloper(tenantId);
        final var developerUser = createNewDeveloperAdminResponse.getUserId();
        final var developerPassword = createNewDeveloperAdminResponse.getPassword();
        developerCli.createToken(developerUser, developerPassword);

        final var createProjectDeveloperResponse = developerCli.createProject(tenantId, projectTitle());
        final var projectId = createProjectDeveloperResponse.getProjectId();
        final var stageId = createProjectDeveloperResponse.getStageId();
        final var secret = createProjectDeveloperResponse.getSecret();

        Thread.sleep(10000);

        final var stageConfig = VersionStageConfigModel.create();
        final var sourceCode = VersionSourceCodeModel.create();
        sourceCode.getFiles().add(new VersionFileModel("main.lua", Base64.getEncoder()
                .encodeToString(script.getBytes(StandardCharsets.UTF_8))));
        final var version = developerCli.createVersion(tenantId, stageId, stageConfig, sourceCode);

        Thread.sleep(10000);

        testClient.connect();
        testClient.signUp(tenantId, stageId, secret);
        final var credentialsMessage = testClient.consumeCredentialsMessage();
        final var credentials = (CredentialsMessageBodyModel) credentialsMessage.getBody();
        final var playerUser = credentials.getUserId();
        final var playerPassword = credentials.getPassword();

        return VersionParameters.builder()
                .tenant(tenantId)
                .developerUser(developerUser)
                .developerPassword(developerPassword)
                .project(projectId)
                .stage(stageId)
                .secret(secret)
                .version(version)
                .playerUser(playerUser)
                .playerPassword(playerPassword)
                .build();
    }

    String tenantTitle() {
        return "title-" + UUID.randomUUID();
    }

    String projectTitle() {
        return "project-" + UUID.randomUUID();
    }
}
