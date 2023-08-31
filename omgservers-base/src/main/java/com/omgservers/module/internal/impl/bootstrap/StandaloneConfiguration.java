package com.omgservers.module.internal.impl.bootstrap;

import com.omgservers.dto.internal.SyncIndexRequest;
import com.omgservers.dto.internal.SyncServiceAccountRequest;
import com.omgservers.model.index.IndexConfigModel;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.factory.IndexModelFactory;
import com.omgservers.module.internal.factory.ServiceAccountModelFactory;
import com.omgservers.operation.getConfig.GetConfigOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StandaloneConfiguration {

    final InternalModule internalModule;

    final GetConfigOperation getConfigOperation;

    final IndexModelFactory indexModelFactory;
    final ServiceAccountModelFactory serviceAccountModelFactory;

    void startup(@Observes @Priority(200) StartupEvent event) {
        log.info("Standalone configuration bootstrap");

        if (getConfigOperation.getConfig().standalone()) {
            Uni.createFrom().voidItem()
                    .flatMap(voidItem -> syncIndex())
                    .flatMap(voidItem -> syncServiceAccount())
                    .invoke(voidItem -> log.info("Standalone configuration was created"))
                    .await().indefinitely();
        } else {
            log.warn("Bootstrap of standalone configuration was skipped");
        }
    }

    Uni<Void> syncIndex() {
        final var indexName = getConfigOperation.getConfig().indexName();
        final var serverUri = getConfigOperation.getConfig().serverUri();
        final var indexConfig = IndexConfigModel.create(Collections.singletonList(serverUri));
        final var indexModel = indexModelFactory.create(indexName, indexConfig);
        final var request = new SyncIndexRequest(indexModel);
        return internalModule.getIndexService().syncIndex(request);
    }

    Uni<Void> syncServiceAccount() {
        final var serviceUsername = getConfigOperation.getConfig().serviceUsername();
        final var servicePassword = getConfigOperation.getConfig().servicePassword();
        final var passwordHash = BcryptUtil.bcryptHash(servicePassword);
        final var serviceAccountModel = serviceAccountModelFactory.create(serviceUsername, passwordHash);
        final var request = new SyncServiceAccountRequest(serviceAccountModel);
        return internalModule.getServiceAccountService().syncServiceAccount(request);
    }
}
