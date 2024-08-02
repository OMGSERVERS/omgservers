package com.omgservers.tester.operation.waitForDeployment;

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

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WaitForDeploymentOperationImpl implements WaitForDeploymentOperation {

    CreateVersionArchiveOperation createVersionArchiveOperation;
    GetLuaFileOperation getLuaFileOperation;

    DeveloperApiTester developerApiTester;
    SupportApiTester supportApiTester;
    AdminApiTester adminApiTester;

    @Override
    public void waitForDeployment(final TestVersionDto testVersion) throws IOException {
        final var developerToken = testVersion.getDeveloperToken();
        final var tenantId = testVersion.getTenantId();
        final var versionId = testVersion.getVersionId();

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
    }
}
