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

        Thread.sleep(1000);

        try {
            final var tenantVersionDetails = developerApiTester.getTenantVersionDetails(testVersion.getDeveloperToken(),
                    testVersion.getTenantId(), testVersion.getTenantVersionId());
            log.info("Tenant version details, {}", tenantVersionDetails);
            assertEquals(1, tenantVersionDetails.getImages().size());
        } finally {
            supportApiTester.deleteTenant(testVersion.getSupportToken(), testVersion.getTenantId());
        }
    }
}
