package com.omgservers.utils.operation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionFileModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.utils.AdminCli;
import com.omgservers.utils.DeveloperCli;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BootstrapVersionOperationImpl implements BootstrapVersionOperation {

    @Inject
    AdminCli adminCli;

    @Inject
    DeveloperCli developerCli;


    @Override
    public VersionParameters bootstrapVersion(String script) throws JsonProcessingException, InterruptedException {
        return bootstrapVersion(script, VersionConfigModel.create());
    }

    @Override
    public VersionParameters bootstrapVersion(String script, VersionConfigModel versionConfig) throws JsonProcessingException, InterruptedException {
        final var tenantId = adminCli.createTenant();

        final var createDeveloperAdminResponse = adminCli.createDeveloper(tenantId);
        final var developerUserId = createDeveloperAdminResponse.getUserId();
        final var developerPassword = createDeveloperAdminResponse.getPassword();

        final var token = developerCli.createDeveloperToken(developerUserId, developerPassword);
        final var createProjectDeveloperResponse = developerCli.createProject(token, tenantId);
        final var projectId = createProjectDeveloperResponse.getProjectId();
        final var stageId = createProjectDeveloperResponse.getStageId();
        final var stageSecret = createProjectDeveloperResponse.getSecret();

        final var sourceCode = VersionSourceCodeModel.create();
        sourceCode.getFiles().add(new VersionFileModel("main.lua", Base64.getEncoder()
                .encodeToString(script.getBytes(StandardCharsets.UTF_8))));
        final var createVersionDeveloperResponse = developerCli.createVersion(token, tenantId, stageId, versionConfig, sourceCode);
        final var versionId = createVersionDeveloperResponse.getId();

        return VersionParameters.builder()
                .tenantId(tenantId)
                .developerUserId(developerUserId)
                .developerPassword(developerPassword)
                .projectId(projectId)
                .stageId(stageId)
                .stageSecret(stageSecret)
                .versionId(versionId)
                .build();
    }
}
