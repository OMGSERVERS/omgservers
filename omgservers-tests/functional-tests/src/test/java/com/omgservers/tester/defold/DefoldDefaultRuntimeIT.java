package com.omgservers.tester.defold;

import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.schema.model.message.body.ServerOutgoingMessageBodyModel;
import com.omgservers.schema.model.version.VersionConfigDto;
import com.omgservers.schema.model.version.VersionGroupDto;
import com.omgservers.schema.model.version.VersionModeDto;
import com.omgservers.tester.BaseTestClass;
import com.omgservers.tester.component.DeveloperApiTester;
import com.omgservers.tester.component.PlayerApiTester;
import com.omgservers.tester.component.SupportApiTester;
import com.omgservers.tester.dto.TestVersionDto;
import com.omgservers.tester.operation.bootstrapTestClient.BootstrapTestClientOperation;
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
import java.util.Collections;
import java.util.Map;

@Slf4j
@QuarkusTest
public class DefoldDefaultRuntimeIT extends BaseTestClass {

    @Inject
    CreateTestVersionOperation createTestVersionOperation;

    @Inject
    BootstrapTestClientOperation bootstrapTestClientOperation;

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

    @Inject
    PlayerApiTester playerApiTester;

    @Test
    void defoldDefaultRuntimeIT() throws Exception {
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

            final var supervisorClient = bootstrapTestClientOperation.bootstrapTestClient(testVersion);

            final var welcomeMessage = playerApiTester.waitMessage(supervisorClient,
                    MessageQualifierEnum.SERVER_WELCOME_MESSAGE);
            final var lobbyAssignment = playerApiTester.waitMessage(supervisorClient,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(welcomeMessage.getId()));
            final var matchmakerAssignment = playerApiTester.waitMessage(supervisorClient,
                    MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(lobbyAssignment.getId()));
            final var matchAssignment = playerApiTester.waitMessage(supervisorClient,
                    MessageQualifierEnum.RUNTIME_ASSIGNMENT_MESSAGE,
                    Collections.singletonList(matchmakerAssignment.getId()));

            try (final var defoldClient1 = startDefoldTestClient(testVersion)) {
                defoldClient1.start();
                log.info("Defold test client has been started, containerName={}", defoldClient1.getContainerName());

                final var serverMessage1 = playerApiTester.waitMessage(supervisorClient,
                        MessageQualifierEnum.SERVER_OUTGOING_MESSAGE,
                        Collections.singletonList(matchAssignment.getId()));
                assertEquals("{text=hello_message}",
                        ((ServerOutgoingMessageBodyModel) serverMessage1.getBody()).getMessage().toString());

                final var serverMessage2 = playerApiTester.waitMessage(supervisorClient,
                        MessageQualifierEnum.SERVER_OUTGOING_MESSAGE,
                        Collections.singletonList(serverMessage1.getId()));
                assertEquals("{text=confirm_message}",
                        ((ServerOutgoingMessageBodyModel) serverMessage2.getBody()).getMessage().toString());
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class TesterNotification {
        Long clientId;
        String text;
    }
}
