package com.omgservers.tester.operation.bootstrapService;

import com.omgservers.tester.component.AdminApiTester;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BootstrapServiceOperationImpl implements BootstrapServiceOperation {

    @Inject
    AdminApiTester adminApiTester;

    @Override
    public void bootstrapService(List<URI> servers, Map<String, String> serviceAccounts) {
        servers.forEach(server -> {
            final var baseUri = server.toString();

            try {
                adminApiTester.createIndex(baseUri, servers);
            } catch (Exception e) {
                log.warn(e.getMessage());
            }

            serviceAccounts.forEach(
                    (username, password) -> {
                        try {
                            adminApiTester.createServiceAccount(baseUri, username, password);
                        } catch (Exception e) {
                            log.warn(e.getMessage());
                        }
                    });
        });
    }
}
