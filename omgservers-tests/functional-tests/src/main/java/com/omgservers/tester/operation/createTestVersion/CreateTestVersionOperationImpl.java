package com.omgservers.tester.operation.createTestVersion;

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
class CreateTestVersionOperationImpl implements CreateTestVersionOperation {

    CreateVersionArchiveOperation createVersionArchiveOperation;
    GetLuaFileOperation getLuaFileOperation;

    DeveloperApiTester developerApiTester;
    SupportApiTester supportApiTester;
    AdminApiTester adminApiTester;

    @Override
    public TestVersionDto createTestVersion() throws IOException {
        return createTestVersion(TenantVersionConfigDto.create());
    }

    @Override
    public TestVersionDto createTestVersion(final TenantVersionConfigDto versionConfig) throws IOException {
        final var adminToken = adminApiTester.createAdminToken();

        final var supportToken = supportApiTester.createSupportToken();
        final var tenantId = supportApiTester.createTenant(supportToken);

        final var createDeveloperAdminResponse = supportApiTester.createDeveloper(supportToken, tenantId);
        final var developerUserId = createDeveloperAdminResponse.getUserId();
        final var developerPassword = createDeveloperAdminResponse.getPassword();

        supportApiTester.createTenantPermissions(supportToken,
                tenantId,
                developerUserId,
                Set.of(TenantPermissionQualifierEnum.PROJECT_MANAGEMENT,
                        TenantPermissionQualifierEnum.GETTING_DASHBOARD));

        final var developerToken = developerApiTester.createDeveloperToken(developerUserId, developerPassword);
        final var createProjectDeveloperResponse = developerApiTester.createTenantProject(developerToken, tenantId);
        final var tenantProjectId = createProjectDeveloperResponse.getTenantProjectId();
        final var tenantStageId = createProjectDeveloperResponse.getTenantStageId();
        final var tenantStageSecret = createProjectDeveloperResponse.getTenantStageSecret();

        final var createVersionDeveloperResponse = developerApiTester
                .createTenantVersion(developerToken, tenantId, tenantProjectId, versionConfig);
        final var tenantVersionId = createVersionDeveloperResponse.getTenantVersionId();

        return TestVersionDto.builder()
                .adminToken(adminToken)
                .supportToken(supportToken)
                .tenantId(tenantId)
                .developerUserId(developerUserId)
                .developerPassword(developerPassword)
                .developerToken(developerToken)
                .tenantProjectId(tenantProjectId)
                .tenantStageId(tenantStageId)
                .tenantStageSecret(tenantStageSecret)
                .tenantVersionId(tenantVersionId)
                .build();
    }
}
