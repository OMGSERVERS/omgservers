package com.omgservers.tester.operation.bootstrapTestVersion;

import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
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
        return bootstrapTestVersion(mainLua, TenantVersionConfigDto.create());
    }

    @Override
    public TestVersionDto bootstrapTestVersion(final String mainLua,
                                               final TenantVersionConfigDto tenantVersionConfig) throws IOException {
        final var adminToken = adminApiTester.createAdminToken();

        final var supportToken = supportApiTester.createSupportToken();
        final var tenantId = supportApiTester.createTenant(supportToken);

        final var createDeveloperAdminResponse = supportApiTester.createDeveloper(supportToken, tenantId);
        final var developerUserId = createDeveloperAdminResponse.getUserId();
        final var developerPassword = createDeveloperAdminResponse.getPassword();

        supportApiTester.createTenantPermissions(supportToken,
                tenantId,
                developerUserId,
                Set.of(TenantPermissionQualifierEnum.PROJECT_MANAGER,
                        TenantPermissionQualifierEnum.TENANT_VIEWER));

        final var developerToken = developerApiTester.createDeveloperToken(developerUserId, developerPassword);
        final var createProjectDeveloperResponse = developerApiTester.createTenantProject(developerToken, tenantId);
        final var tenantProjectId = createProjectDeveloperResponse.getProjectId();
        final var tenantStageId = createProjectDeveloperResponse.getStageId();

        final var createTenantVersionDeveloperResponse =
                developerApiTester.createTenantVersion(developerToken, tenantId, tenantProjectId, tenantVersionConfig);
        final var tenantVersionId = createTenantVersionDeveloperResponse.getVersionId();

        final var buildTenantVersionDeveloperResponse = developerApiTester
                .uploadFilesArchive(developerToken, tenantId, tenantVersionId, mainLua);
        final var tenantFilesArchiveId = buildTenantVersionDeveloperResponse.getFilesArchiveId();

        waitForBuilding(developerToken, tenantId, tenantVersionId);

        final var deployVersionDeveloperResponse = developerApiTester
                .deployTenantVersion(developerToken, tenantId, tenantProjectId, tenantStageId, tenantVersionId);
        final var tenantDeploymentId = deployVersionDeveloperResponse.getDeploymentId();

        waitForDeployment(developerToken, tenantId, tenantDeploymentId);

        return TestVersionDto.builder()
                .adminToken(adminToken)
                .supportToken(supportToken)
                .tenantId(tenantId)
                .developerUserId(developerUserId)
                .developerPassword(developerPassword)
                .developerToken(developerToken)
                .tenantProjectId(tenantProjectId)
                .tenantStageId(tenantStageId)
                .tenantVersionId(tenantVersionId)
                .tenantFilesArchiveId(tenantFilesArchiveId)
                .tenantDeploymentId(tenantDeploymentId)
                .build();
    }

    private void waitForBuilding(final String developerToken,
                                 final Long tenantId,
                                 final Long tenantVersionId) throws IOException {
        var currentTenantVersionDetails = developerApiTester
                .getTenantVersionDetails(developerToken, tenantId, tenantVersionId);
        var attempt = 1;
        var maxAttempts = 12;
        while ((currentTenantVersionDetails.getImages().isEmpty()) &&
                attempt < maxAttempts) {
            try {
                log.info("Waiting for building, attempt={}", attempt);
                Thread.sleep((long) attempt * 2 * 1000);
                currentTenantVersionDetails = developerApiTester
                        .getTenantVersionDetails(developerToken, tenantId, tenantVersionId);
                attempt++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (attempt < maxAttempts) {
            log.info("Version was built, tenantVersionId={}", tenantVersionId);
        } else {
            throw new IllegalStateException("Version was not built, tenantVersionId=" + tenantVersionId);
        }
    }

    private void waitForDeployment(final String developerToken,
                                   final Long tenantId,
                                   final Long tenantDeploymentId) throws IOException {
        var currentTenantDeploymentDetails = developerApiTester
                .getTenantDeploymentDetails(developerToken, tenantId, tenantDeploymentId);
        var attempt = 1;
        var maxAttempts = 12;
        while (currentTenantDeploymentDetails.getMatchmakerRefs().isEmpty() &&
                attempt < maxAttempts) {
            try {
                log.info("Waiting for deployment, attempt={}", attempt);
                Thread.sleep((long) attempt * 2 * 1000);
                currentTenantDeploymentDetails = developerApiTester
                        .getTenantDeploymentDetails(developerToken, tenantId, tenantDeploymentId);
                attempt++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (attempt < maxAttempts) {
            log.info("Version was deployed, tenantDeploymentId={}", tenantDeploymentId);
        } else {
            throw new IllegalStateException("Version was not deployed, tenantDeploymentId=" + tenantDeploymentId);
        }
    }
}
