//package com.omgservers.tester.developer;
//
//import com.omgservers.tester.BaseTestClass;
//import com.omgservers.tester.component.DeveloperApiTester;
//import com.omgservers.tester.component.SupportApiTester;
//import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
//import io.quarkus.test.junit.QuarkusTest;
//import jakarta.inject.Inject;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//
//@Slf4j
//@QuarkusTest
//public class DeveloperGetTenantDetailsIT extends BaseTestClass {
//
//    @Inject
//    BootstrapTestVersionOperation bootstrapTestVersionOperation;
//
//    @Inject
//    SupportApiTester supportApiTester;
//
//    @Inject
//    DeveloperApiTester developerApiTester;
//
//    @Test
//    void getTenantDetailsIT() throws Exception {
//        final var version = bootstrapTestVersionOperation.bootstrapTestVersion(
//                """
//                        local omgserver = require("omgserver")
//                        omgserver:enter_loop({
//                            handle = function(self, command_qualifier, command_body)
//                            end,
//                        })
//                        """);
//
//        try {
//            final var tenantDetails = developerApiTester.getTenantDetails(version.getDeveloperToken(),
//                    version.getTenantId());
//            log.info("Tenant details, {}", tenantDetails);
//
//            assertNotNull(tenantDetails);
////            assertEquals(1, tenantDetails.getProjects().size());
////            assertEquals(1, tenantDetails.getStages().size());
////            assertEquals(1, tenantDetails.getVersions().size());
////            assertEquals(1, tenantDetails.getLobbyRefs().size());
////            assertEquals(1, tenantDetails.getMatchmakerRefs().size());
//        } finally {
//            supportApiTester.deleteTenant(version.getSupportToken(), version.getTenantId());
//        }
//    }
//}
