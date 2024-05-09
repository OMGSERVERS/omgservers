package com.omgservers.tester.developer;

import com.omgservers.tester.BaseTestClass;
import com.omgservers.tester.component.DeveloperApiTester;
import com.omgservers.tester.component.SupportApiTester;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class DeveloperGetTenantDashboardIT extends BaseTestClass {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    SupportApiTester supportApiTester;

    @Inject
    DeveloperApiTester developerApiTester;

    @Test
    void getTenantDashboardIT() throws Exception {
        final var version = bootstrapTestVersionOperation.bootstrapTestVersion(
                """
                        require("omgservers").enter_loop(function(self, command)
                        end)
                        """);

        Thread.sleep(32_000);

        try {
            final var tenantDashboard = developerApiTester.getTenantDashboard(version.getDeveloperToken(),
                    version.getTenantId());
            log.info("Tenant dashboard, {}", tenantDashboard);

            assertNotNull(tenantDashboard);
            assertEquals(1, tenantDashboard.getProjects().size());
            assertEquals(1, tenantDashboard.getStages().size());
            assertEquals(1, tenantDashboard.getVersions().size());
            assertEquals(1, tenantDashboard.getLobbyRefs().size());
            assertEquals(1, tenantDashboard.getMatchmakerLobbyRefs().size());
        } finally {
            supportApiTester.deleteTenant(version.getTenantId());
        }
    }
}
