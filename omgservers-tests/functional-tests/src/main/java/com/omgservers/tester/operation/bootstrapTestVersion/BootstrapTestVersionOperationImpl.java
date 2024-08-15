package com.omgservers.tester.operation.bootstrapTestVersion;

import com.omgservers.schema.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.schema.model.version.VersionConfigDto;
import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.DeveloperApiTester;
import com.omgservers.tester.component.SupportApiTester;
import com.omgservers.tester.dto.TestVersionDto;
import com.omgservers.tester.operation.createVersionArchive.CreateVersionArchiveOperation;
import com.omgservers.tester.operation.getLuaFile.GetLuaFileOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Set;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BootstrapTestVersionOperationImpl implements BootstrapTestVersionOperation {

    CreateVersionArchiveOperation createVersionArchiveOperation;
    GetLuaFileOperation getLuaFileOperation;

    DeveloperApiTester developerApiTester;
    SupportApiTester supportApiTester;
    AdminApiTester adminApiTester;

    @Override
    public TestVersionDto bootstrapTestVersion(final String mainLua) throws IOException {
        return bootstrapTestVersion(mainLua, VersionConfigDto.create());
    }

    @Override
    public TestVersionDto bootstrapTestVersion(final String mainLua,
                                               final VersionConfigDto versionConfig) throws IOException {
        final var adminToken = adminApiTester.createAdminToken();

        final var supportToken = supportApiTester.createSupportToken();
        final var tenantId = supportApiTester.createTenant(supportToken);

        final var createDeveloperAdminResponse = supportApiTester.createDeveloper(supportToken, tenantId);
        final var developerUserId = createDeveloperAdminResponse.getUserId();
        final var developerPassword = createDeveloperAdminResponse.getPassword();

        supportApiTester.createTenantPermissions(supportToken,
                tenantId,
                developerUserId,
                Set.of(TenantPermissionEnum.PROJECT_MANAGEMENT, TenantPermissionEnum.GETTING_DASHBOARD));

        final var developerToken = developerApiTester.createDeveloperToken(developerUserId, developerPassword);
        final var createProjectDeveloperResponse = developerApiTester.createProject(developerToken, tenantId);
        final var projectId = createProjectDeveloperResponse.getProjectId();
        final var stageId = createProjectDeveloperResponse.getStageId();
        final var stageSecret = createProjectDeveloperResponse.getSecret();

        final var createVersionDeveloperResponse = developerApiTester
                .buildVersion(developerToken, tenantId, stageId, versionConfig, mainLua);
        final var versionId = createVersionDeveloperResponse.getId();

        var currentVersionDashboard = developerApiTester.getVersionDashboard(developerToken, tenantId, versionId);
        var attempt = 1;
        var maxAttempts = 12;
        while ((currentVersionDashboard.getVersion().getLobbyRefs().isEmpty() ||
                currentVersionDashboard.getVersion().getMatchmakerRefs().isEmpty()) &&
                attempt < maxAttempts) {
            try {
                log.info("Waiting for deployment, attempt={}", attempt);
                Thread.sleep((long) attempt * 2 * 1000);
                currentVersionDashboard = developerApiTester.getVersionDashboard(developerToken, tenantId, versionId);
                attempt++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (attempt < maxAttempts) {
            log.info("Version was deployed, version={}", versionId);
        } else {
            throw new IllegalStateException("Version was not deployed, versionId=" + versionId);
        }

        return TestVersionDto.builder()
                .adminToken(adminToken)
                .supportToken(supportToken)
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
