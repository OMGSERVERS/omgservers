package com.omgservers.platforms.integrationtest.operations.bootstrapEnvironmentOperation;

import com.omgservers.application.module.internalModule.model.index.IndexConfigModel;
import com.omgservers.application.module.internalModule.model.index.IndexModel;
import com.omgservers.application.module.internalModule.model.index.IndexModelFactory;
import com.omgservers.platforms.integrationtest.cli.AdminCli;
import com.omgservers.platforms.integrationtest.operations.bootstrapServerOperation.BootstrapServerOperation;
import com.omgservers.platforms.integrationtest.operations.getConfigOperation.GetConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BootstrapEnvironmentOperationImpl implements BootstrapEnvironmentOperation {

    static final AtomicBoolean finished = new AtomicBoolean(false);

    final BootstrapServerOperation bootstrapServerOperation;
    final GetConfigOperation getConfigOperation;

    final IndexModelFactory indexModelFactory;

    final AdminCli adminCli;

    @Override
    public void bootstrap() {
        if (finished.get()) {
            log.info("Environment was already bootstrapped, skip operation");
            return;
        }

        var indexName = getConfigOperation.getConfig().environment().indexName();
        var config = IndexConfigModel.create(getConfigOperation.getInternalAddresses());
        var index = indexModelFactory.create(indexName, config);

        var testerUsername = getConfigOperation.getConfig().tester().serviceUsername();
        var testerPassword = getConfigOperation.getConfig().tester().servicePassword();
        var serviceAccounts = getConfigOperation.getServiceAccounts();
        serviceAccounts.put(testerUsername, testerPassword);

        var servers = getConfigOperation.getServers();
        servers.forEach(server -> bootstrapServerOperation
                .bootstrap(server.externalAddress(), index, serviceAccounts));

        finished.set(true);
        log.info("Environment was bootstrapped");
    }
}
