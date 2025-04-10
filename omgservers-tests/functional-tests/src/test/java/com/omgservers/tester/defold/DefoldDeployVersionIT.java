//package com.omgservers.tester.defold;
//
//import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
//import com.omgservers.tester.BaseTestClass;
//import com.omgservers.tester.component.DeveloperApiTester;
//import com.omgservers.tester.component.SupportApiTester;
//import com.omgservers.tester.operation.createTestVersion.CreateTestVersionOperation;
//import com.omgservers.tester.operation.getDockerClient.GetDockerClientOperation;
//import com.omgservers.tester.operation.pushTestVersionImage.PushTestVersionImageOperation;
//import io.quarkus.test.junit.QuarkusTest;
//import jakarta.inject.Inject;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//
//@Slf4j
//@QuarkusTest
//public class DefoldDeployVersionIT extends BaseTestClass {
//
//    @Inject
//    CreateTestVersionOperation createTestVersionOperation;
//
//    @Inject
//    GetDockerClientOperation getDockerClientOperation;
//
//    @Inject
//    PushTestVersionImageOperation pushTestVersionImageOperation;
//
//    @Inject
//    DeveloperApiTester developerApiTester;
//
//    @Inject
//    SupportApiTester supportApiTester;
//
//    @Test
//    void defoldDeployVersionIT() throws Exception {
//        final var tenatnVersionConfig = TenantVersionConfigDto.create();
//        tenatnVersionConfig.setUserData(new UserData(DefoldDeployVersionIT.class.getSimpleName()));
//        final var testVersion = createTestVersionOperation.createTestVersion(tenatnVersionConfig);
//
//        final var developerUserId = testVersion.getDeveloperUserId();
//        final var developerPassword = testVersion.getDeveloperPassword();
//
//        try {
//            final var dockerClient = getDockerClientOperation.getDockerClient(developerUserId, developerPassword);
//            pushTestVersionImageOperation.pushTestVersionImage(dockerClient,
//                    "omgservers/defold-test-runtime:1.0.0-SNAPSHOT",
//                    testVersion);
//
//            Thread.sleep(1000);
//
//            final var tenantVersionDetails = developerApiTester
//                    .getTenantVersionDetails(testVersion.getDeveloperToken(),
//                            testVersion.getTenantId(),
//                            testVersion.getTenantVersionId());
//            log.info("Version details, {}", tenantVersionDetails);
//            assertEquals(1, tenantVersionDetails.getImages().size());
//
//            final var tenantDeploymentId = developerApiTester.deployTenantVersion(testVersion.getDeveloperToken(),
//                    testVersion.getTenantId(),
//                    testVersion.getTenantProjectId(),
//                    testVersion.getTenantStageId(),
//                    testVersion.getTenantVersionId()).getDeploymentId();
//            testVersion.setTenantDeploymentId(tenantDeploymentId);
//
//        } finally {
//            supportApiTester.deleteTenant(testVersion.getSupportToken(), testVersion.getTenantId());
//        }
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    class UserData {
//        String testId;
//    }
//}
