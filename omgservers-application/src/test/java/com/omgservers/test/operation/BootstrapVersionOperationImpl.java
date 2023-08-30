package com.omgservers.test.operation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.omgservers.model.version.VersionFileModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.model.version.VersionStatusEnum;
import com.omgservers.test.AdminCli;
import com.omgservers.test.DeveloperCli;
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
    public void bootstrapVersion(String script) throws JsonProcessingException, InterruptedException {
        bootstrapVersion(script, VersionStageConfigModel.create());
    }

    @Override
    public void bootstrapVersion(String script, VersionStageConfigModel stageConfig) throws JsonProcessingException, InterruptedException {
        final var tenantId = adminCli.createTenant();

        final var createDeveloperAdminResponse = adminCli.createDeveloper(tenantId);
        final var developerUserId = createDeveloperAdminResponse.getUserId();
        final var developerPassword = createDeveloperAdminResponse.getPassword();

        final var token = developerCli.createDeveloperToken(developerUserId, developerPassword);
        final var createProjectDeveloperResponse = developerCli.createProject(token, tenantId);
        final var projectId = createProjectDeveloperResponse.getProjectId();
        final var stageId = createProjectDeveloperResponse.getStageId();
        final var secret = createProjectDeveloperResponse.getSecret();

        Thread.sleep(5000);

        final var sourceCode = VersionSourceCodeModel.create();
        sourceCode.getFiles().add(new VersionFileModel("main.lua", Base64.getEncoder()
                .encodeToString(script.getBytes(StandardCharsets.UTF_8))));
        final var createVersionDeveloperResponse = developerCli.createVersion(token, tenantId, stageId, stageConfig, sourceCode);
        final var version = createVersionDeveloperResponse.getId();

        var status = developerCli.getVersionStatus(token, version);
        while (status == VersionStatusEnum.NEW) {
            Thread.sleep(1000);
            status = developerCli.getVersionStatus(token, version);
        }
    }
}
