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
        final var tenantDeploymentId = testVersion.getTenantDeploymentId();

        var currentVersionDashboard = developerApiTester
                .getTenantDeploymentDashboard(developerToken, tenantId, tenantDeploymentId);
        var attempt = 1;
        var maxAttempts = 12;
        while ((currentVersionDashboard.getLobbyRefs().isEmpty() ||
                currentVersionDashboard.getMatchmakerRefs().isEmpty()) &&
                attempt < maxAttempts) {
            try {
                log.info("Waiting for deployment, attempt={}", attempt);
                Thread.sleep((long) attempt * 2 * 1000);
                currentVersionDashboard = developerApiTester.getTenantDeploymentDashboard(developerToken,
                        tenantId,
                        tenantDeploymentId);
                attempt++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        final var tenantVersionId = testVersion.getTenantVersionId();
        if (attempt < maxAttempts) {
            log.info("Version was deployed, tenantVersionId={}, tenantDeploymentId={}",
                    tenantVersionId, tenantDeploymentId);
        } else {
            throw new IllegalStateException("Version was not deployed, tenantVersionId=" + tenantVersionId);
        }
    }
}
