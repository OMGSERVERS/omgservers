package com.omgservers.test.operations.bootstrapServerOperation;

import com.omgservers.model.index.IndexConfigModel;
import com.omgservers.test.cli.AdminCli;
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
    public void bootstrap(URI uri, String indexName, IndexConfigModel indexConfig, Map<String, String> serviceAccounts) {
        adminCli.createClient(uri);
        adminCli.createIndex(indexName, indexConfig);
        serviceAccounts.forEach(adminCli::createServiceAccount);

        log.info("Server was bootstrap, uri={}", uri);
    }
}
