package com.omgservers.tester.defold;

import com.omgservers.schema.model.version.VersionConfigDto;
import com.omgservers.schema.model.version.VersionGroupDto;
import com.omgservers.schema.model.version.VersionModeDto;
import com.omgservers.tester.BaseTestClass;
import com.omgservers.tester.component.DeveloperApiTester;
import com.omgservers.tester.component.SupportApiTester;
import com.omgservers.tester.dto.TestVersionDto;
import com.omgservers.tester.operation.createTestVersion.CreateTestVersionOperation;
import com.omgservers.tester.operation.getDockerClient.GetDockerClientOperation;
import com.omgservers.tester.operation.pushTestVersionImage.PushTestVersionImageOperation;
import com.omgservers.tester.operation.waitForDeployment.WaitForDeploymentOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@Slf4j
@QuarkusTest
public class DefoldLobbyRuntimeIT extends BaseTestClass {

    @Inject
    CreateTestVersionOperation createTestVersionOperation;

    @Inject
    GetDockerClientOperation getDockerClientOperation;

    @Inject
    PushTestVersionImageOperation pushTestVersionImageOperation;

    @Inject
    WaitForDeploymentOperation waitForDeploymentOperation;

    @Inject
    DeveloperApiTester developerApiTester;

    @Inject
    SupportApiTester supportApiTester;

    @Test
    void defoldUpgradeConnectionIT() throws Exception {
        final var versionConfig = VersionConfigDto.create();
        versionConfig.setModes(new ArrayList<>() {{
            add(VersionModeDto.create("test", 1, 16, new ArrayList<>() {{
                add(new VersionGroupDto("players", 1, 16));
            }}));
        }});
        versionConfig.setUserData(new UserData("deploy-version"));
        final var testVersion = createTestVersionOperation.createTestVersion(versionConfig);

        final var developerUserId = testVersion.getDeveloperUserId();
        final var developerPassword = testVersion.getDeveloperPassword();

        try {
            final var dockerClient = getDockerClientOperation.getDockerClient(developerUserId, developerPassword);
            pushTestVersionImageOperation.pushTestVersionImage(dockerClient,
                    "omgservers/defold-test-runtime:1.0.0-SNAPSHOT",
                    testVersion);

            Thread.sleep(1000);

            developerApiTester.deployVersion(testVersion.getDeveloperToken(), testVersion.getTenantId(),
                    testVersion.getVersionId());

            waitForDeploymentOperation.waitForDeployment(testVersion);

            try (final var defoldClient1 = startDefoldTestClient(testVersion)) {
                defoldClient1.start();

                log.info("Defold test client started");
            }

        } finally {
            supportApiTester.deleteTenant(testVersion.getSupportToken(), testVersion.getTenantId());
        }
    }

    private GenericContainer startDefoldTestClient(final TestVersionDto testVersion) {
        return new GenericContainer("omgservers/defold-test-client:1.0.0-SNAPSHOT")
                .withEnv(Map.of("OMGSERVERS_URL", "http://localhost:8080",
                        "OMGSERVERS_TENANT_ID", testVersion.getTenantId().toString(),
                        "OMGSERVERS_STAGE_ID", testVersion.getStageId().toString(),
                        "OMGSERVERS_STAGE_SECRET", testVersion.getStageSecret()))
                .withNetworkMode("host");

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class UserData {
        String testId;
    }
}
