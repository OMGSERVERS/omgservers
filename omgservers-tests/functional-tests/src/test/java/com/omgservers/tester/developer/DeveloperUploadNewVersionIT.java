//package com.omgservers.tester.developer;
//
//import com.omgservers.tester.BaseTestClass;
//import com.omgservers.tester.component.SupportApiTester;
//import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
//import com.omgservers.tester.operation.buildTestVersion.BuildTestVersionOperation;
//import io.quarkus.test.junit.QuarkusTest;
//import jakarta.inject.Inject;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//
//@Slf4j
//@QuarkusTest
//public class DeveloperUploadNewVersionIT extends BaseTestClass {
//
//    @Inject
//    BootstrapTestVersionOperation bootstrapTestVersionOperation;
//
//    @Inject
//    BuildTestVersionOperation buildTestVersionOperation;
//
//    @Inject
//    SupportApiTester supportApiTester;
//
//    @Test
//    void uploadNewVersionIT() throws Exception {
//        final var testVersion = bootstrapTestVersionOperation.bootstrapTestVersion(
//                """
//                        local omgserver = require("omgserver")
//                        omgserver:enter_loop({
//                            handle = function(self, command_qualifier, command_body)
//                            end,
//                        })
//                        """);
//
//        try {
//            buildTestVersionOperation.buildTestVersion(testVersion, """
//                    """);
//
//            Thread.sleep(32_000);
//        } finally {
//            supportApiTester.deleteTenant(testVersion.getSupportToken(), testVersion.getTenantId());
//        }
//    }
//}
