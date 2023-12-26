package com.omgservers.service.module.system.impl.bootstrap;

import com.omgservers.model.index.IndexConfigModel;
import com.omgservers.service.ServiceApplication;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.IndexModelFactory;
import com.omgservers.service.factory.ServiceAccountModelFactory;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BootstrapService {

    final SystemModule systemModule;

    final GetConfigOperation getConfigOperation;

    final IndexModelFactory indexModelFactory;
    final ServiceAccountModelFactory serviceAccountModelFactory;

    @WithSpan
    void startup(@Observes @Priority(ServiceApplication.START_UP_BOOTSTRAP_SERVICE_PRIORITY) StartupEvent event) {
        if (getConfigOperation.getConfig().bootstrapService()) {
            Uni.createFrom().voidItem()
                    .flatMap(voidItem -> createIndex())
                    .flatMap(wasIndexCreated -> createServiceAccount())
                    .await().indefinitely();
        } else {
            log.warn("Bootstrap service was disabled, skip operation");
        }
    }

    Uni<Void> createIndex() {
        final var indexName = getConfigOperation.getConfig().indexName();

        return systemModule.getShortcutService().findIndex(indexName)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    final var addresses = getConfigOperation.getConfig().addresses();
                    final var shardCount = getConfigOperation.getConfig().shardCount();
                    final var indexConfig = IndexConfigModel.create(addresses, shardCount);
                    final var indexModel = indexModelFactory.create(indexName, indexConfig);

                    log.info("Bootstrap index, name={}, addressed={}, shards={}",
                            indexName,
                            addresses.size(),
                            shardCount);

                    return systemModule.getShortcutService().syncIndex(indexModel)
                            .replaceWith(indexModel);
                })
                .replaceWithVoid();
    }

    Uni<Void> createServiceAccount() {
        final var serviceUsername = getConfigOperation.getConfig().serviceUsername();

        return systemModule.getShortcutService().findServiceAccount(serviceUsername)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    final var servicePassword = getConfigOperation.getConfig().servicePassword();
                    final var passwordHash = BcryptUtil.bcryptHash(servicePassword);
                    final var serviceAccount = serviceAccountModelFactory.create(serviceUsername, passwordHash);

                    log.info("Bootstrap service account, username={}", serviceUsername);

                    return systemModule.getShortcutService().syncServiceAccount(serviceAccount)
                            .replaceWith(serviceAccount);
                })
                .replaceWithVoid();
    }
}
