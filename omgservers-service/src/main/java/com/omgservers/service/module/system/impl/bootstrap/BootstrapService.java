package com.omgservers.service.module.system.impl.bootstrap;

import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.model.dto.system.FindServiceAccountRequest;
import com.omgservers.model.dto.system.FindServiceAccountResponse;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.SyncServiceAccountResponse;
import com.omgservers.model.index.IndexConfigModel;
import com.omgservers.model.index.IndexModel;
import com.omgservers.model.serviceAccount.ServiceAccountModel;
import com.omgservers.service.ServiceConfiguration;
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
    void startup(@Observes @Priority(ServiceConfiguration.START_UP_BOOTSTRAP_SERVICE_PRIORITY) StartupEvent event) {
        if (getConfigOperation.getServiceConfig().bootstrapService()) {
            Uni.createFrom().voidItem()
                    .flatMap(voidItem -> createIndex())
                    .flatMap(wasIndexCreated -> createServiceAccount())
                    .await().indefinitely();
        } else {
            log.warn("Bootstrap service was disabled, skip operation");
        }
    }

    Uni<Void> createIndex() {
        final var indexName = getConfigOperation.getServiceConfig().indexName();

        return findIndex(indexName)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    final var addresses = getConfigOperation.getServiceConfig().addresses();
                    final var shardCount = getConfigOperation.getServiceConfig().shardCount();
                    final var indexConfig = IndexConfigModel.create(addresses, shardCount);
                    final var indexModel = indexModelFactory.create(indexName, indexConfig);

                    log.info("Bootstrap index, name={}, addressed={}, shards={}",
                            indexName,
                            addresses.size(),
                            shardCount);

                    return syncIndex(indexModel)
                            .replaceWith(indexModel);
                })
                .replaceWithVoid();
    }

    Uni<IndexModel> findIndex(final String indexName) {
        final var request = new FindIndexRequest(indexName);
        return systemModule.getIndexService().findIndex(request)
                .map(FindIndexResponse::getIndex);
    }

    Uni<Boolean> syncIndex(final IndexModel index) {
        final var request = new SyncIndexRequest(index);
        return systemModule.getIndexService().syncIndex(request)
                .map(SyncIndexResponse::getCreated);
    }

    Uni<Void> createServiceAccount() {
        final var serviceUsername = getConfigOperation.getServiceConfig().serviceUsername();

        return findServiceAccount(serviceUsername)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    final var servicePassword = getConfigOperation.getServiceConfig().servicePassword();
                    final var passwordHash = BcryptUtil.bcryptHash(servicePassword);
                    final var serviceAccount = serviceAccountModelFactory.create(serviceUsername, passwordHash);

                    log.info("Bootstrap service account, username={}", serviceUsername);

                    return syncServiceAccount(serviceAccount)
                            .replaceWith(serviceAccount);
                })
                .replaceWithVoid();
    }

    Uni<ServiceAccountModel> findServiceAccount(final String username) {
        final var request = new FindServiceAccountRequest(username);
        return systemModule.getServiceAccountService().findServiceAccount(request)
                .map(FindServiceAccountResponse::getServiceAccount);
    }

    Uni<Boolean> syncServiceAccount(final ServiceAccountModel serviceAccount) {
        final var request = new SyncServiceAccountRequest(serviceAccount);
        return systemModule.getServiceAccountService().syncServiceAccount(request)
                .map(SyncServiceAccountResponse::getCreated);
    }
}
