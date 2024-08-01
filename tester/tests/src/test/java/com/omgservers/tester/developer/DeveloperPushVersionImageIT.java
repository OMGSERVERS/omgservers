package com.omgservers.tester.developer;

import com.omgservers.tester.BaseTestClass;
import com.omgservers.tester.component.DeveloperApiTester;
import com.omgservers.tester.component.SupportApiTester;
import com.omgservers.tester.operation.createTestVersion.CreateTestVersionOperation;
import com.omgservers.tester.operation.getDockerClient.GetDockerClientOperation;
import com.omgservers.tester.operation.pushTestVersionImage.PushTestVersionImageOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class DeveloperPushVersionImageIT extends BaseTestClass {

    @Inject
    CreateTestVersionOperation createTestVersionOperation;

    @Inject
    GetDockerClientOperation getDockerClientOperation;

    @Inject
    PushTestVersionImageOperation pushTestVersionImageOperation;

    @Inject
    DeveloperApiTester developerApiTester;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void developerPushVersionImageIT() throws Exception {
        final var testVersion = createTestVersionOperation.createTestVersion();

        final var developerUserId = testVersion.getDeveloperUserId();
        final var developerPassword = testVersion.getDeveloperPassword();

        final var dockerClient = getDockerClientOperation.getDockerClient(developerUserId, developerPassword);
        pushTestVersionImageOperation.pushTestVersionImage(dockerClient,
                "omgservers/defold-test-runtime:1.0.0-SNAPSHOT",
                testVersion);

        try {
            final var versionDashboard = developerApiTester.getVersionDashboard(testVersion.getDeveloperToken(),
                    testVersion.getTenantId(), testVersion.getVersionId());
            log.info("Version dashboard, {}", versionDashboard);
            assertEquals(1, versionDashboard.getVersion().getImageRefs().size());
        } finally {
            supportApiTester.deleteTenant(testVersion.getSupportToken(), testVersion.getTenantId());
        }
    }
}
