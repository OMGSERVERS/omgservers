package com.omgservers.tester.operation.bootstrapTestVersion;

import com.omgservers.model.file.EncodedFileModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.DeveloperApiTester;
import com.omgservers.tester.model.TestVersionModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BootstrapTestVersionOperationImpl implements BootstrapTestVersionOperation {

    @Inject
    AdminApiTester adminApiTester;

    @Inject
    DeveloperApiTester developerApiTester;

    @Override
    public TestVersionModel bootstrapTestVersion(final String lobby,
                                                 final String match) throws IOException {
        return bootstrapTestVersion(lobby, match, VersionConfigModel.create());
    }

    @Override
    public TestVersionModel bootstrapTestVersion(final String lobby,
                                                 final String match,
                                                 final VersionConfigModel versionConfig) throws IOException {
        final var tenantId = adminApiTester.createTenant();

        final var createDeveloperAdminResponse = adminApiTester.createDeveloper(tenantId);
        final var developerUserId = createDeveloperAdminResponse.getUserId();
        final var developerPassword = createDeveloperAdminResponse.getPassword();

        final var developerToken = developerApiTester.createDeveloperToken(developerUserId, developerPassword);
        final var createProjectDeveloperResponse = developerApiTester.createProject(developerToken, tenantId);
        final var projectId = createProjectDeveloperResponse.getProjectId();
        final var stageId = createProjectDeveloperResponse.getStageId();
        final var stageSecret = createProjectDeveloperResponse.getSecret();

        final var sourceCode = VersionSourceCodeModel.create();
        sourceCode.getFiles().add(new EncodedFileModel("lobby.lua", Base64.getEncoder()
                .encodeToString(lobby.getBytes(StandardCharsets.UTF_8))));
        sourceCode.getFiles().add(new EncodedFileModel("match.lua", Base64.getEncoder()
                .encodeToString(match.getBytes(StandardCharsets.UTF_8))));
        final var createVersionDeveloperResponse =
                developerApiTester.createVersion(developerToken, tenantId, stageId, versionConfig, sourceCode);
        final var versionId = createVersionDeveloperResponse.getId();

        return TestVersionModel.builder()
                .tenantId(tenantId)
                .developerUserId(developerUserId)
                .developerPassword(developerPassword)
                .developerToken(developerToken)
                .projectId(projectId)
                .stageId(stageId)
                .stageSecret(stageSecret)
                .versionId(versionId)
                .build();
    }
}
