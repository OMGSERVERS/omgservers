package com.omgservers.platforms.integrationtest.operations.bootstrapServerOperation;

import com.omgservers.application.module.internalModule.model.index.IndexModel;
import com.omgservers.platforms.integrationtest.cli.AdminCli;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BootstrapServerOperationImpl implements BootstrapServerOperation {

    final AdminCli adminCli;

    @Override
    public void bootstrap(URI uri, IndexModel index, Map<String, String> serviceAccounts) {
        adminCli.createClient(uri);
        adminCli.syncIndex(index);
        serviceAccounts.forEach(adminCli::createServiceAccount);

        log.info("Server was bootstrap, uri={}", uri);
    }
}
