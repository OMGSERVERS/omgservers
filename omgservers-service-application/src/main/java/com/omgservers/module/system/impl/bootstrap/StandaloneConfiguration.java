package com.omgservers.module.system.impl.bootstrap;

import com.omgservers.model.dto.internal.SyncIndexRequest;
import com.omgservers.model.dto.internal.SyncServiceAccountRequest;
import com.omgservers.model.index.IndexConfigModel;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.factory.IndexModelFactory;
import com.omgservers.module.system.factory.ServiceAccountModelFactory;
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

    final SystemModule systemModule;

    final GetConfigOperation getConfigOperation;

    final IndexModelFactory indexModelFactory;
    final ServiceAccountModelFactory serviceAccountModelFactory;

    void startup(@Observes @Priority(2000) StartupEvent event) {
        if (getConfigOperation.getConfig().standalone()) {
            Uni.createFrom().voidItem()
                    .flatMap(voidItem -> syncIndex())
                    .flatMap(voidItem -> syncServiceAccount())
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
        return systemModule.getIndexService().syncIndex(request);
    }

    Uni<Void> syncServiceAccount() {
        final var serviceUsername = getConfigOperation.getConfig().serviceUsername();
        final var servicePassword = getConfigOperation.getConfig().servicePassword();
        final var passwordHash = BcryptUtil.bcryptHash(servicePassword);
        final var serviceAccountModel = serviceAccountModelFactory.create(serviceUsername, passwordHash);
        final var request = new SyncServiceAccountRequest(serviceAccountModel);
        return systemModule.getServiceAccountService().syncServiceAccount(request);
    }
}
