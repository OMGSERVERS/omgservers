package com.omgservers.application.module.bootstrapModule.impl.service.bootstrapHelpService.impl.method.bootstrapStandaloneConfigurationMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.SyncIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.SyncServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.model.index.IndexConfigModel;
import com.omgservers.application.module.internalModule.model.index.IndexModelFactory;
import com.omgservers.application.module.internalModule.model.serviceAccount.ServiceAccountModelFactory;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.operation.getConfigOperation.GetConfigOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapStandaloneConfigurationMethodImpl implements BootstrapStandaloneConfigurationMethod {

    final InternalModule internalModule;
    final TenantModule tenantModule;

    final GetConfigOperation getConfigOperation;

    final IndexModelFactory indexModelFactory;
    final ServiceAccountModelFactory serviceAccountModelFactory;

    @Override
    public Uni<Void> bootstrapStandaloneConfiguration() {
        if (getConfigOperation.getConfig().standalone()) {
            return Uni.createFrom().voidItem()
                    .flatMap(voidItem -> syncIndex())
                    .flatMap(voidItem -> syncServiceAccount())
                    .invoke(voidItem -> log.info("Standalone configuration was created"));
        } else {
            return Uni.createFrom().voidItem()
                    .invoke(voidItem -> log.warn("Bootstrap of standalone configuration was skipped"));
        }
    }

    Uni<Void> syncIndex() {
        final var indexName = getConfigOperation.getConfig().indexName();
        final var serverUri = getConfigOperation.getConfig().serverUri();
        final var indexConfig = IndexConfigModel.create(Collections.singletonList(serverUri));
        final var indexModel = indexModelFactory.create(indexName, indexConfig);
        final var request = new SyncIndexHelpRequest(indexModel);
        return internalModule.getIndexInternalService().syncIndex(request);
    }

    Uni<Void> syncServiceAccount() {
        final var serviceUsername = getConfigOperation.getConfig().serviceUsername();
        final var servicePassword = getConfigOperation.getConfig().servicePassword();
        final var passwordHash = BcryptUtil.bcryptHash(servicePassword);
        final var serviceAccountModel = serviceAccountModelFactory.create(serviceUsername, passwordHash);
        final var request = new SyncServiceAccountHelpRequest(serviceAccountModel);
        return internalModule.getServiceAccountHelpService().syncServiceAccount(request);
    }
}
