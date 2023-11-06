package com.omgservers.service.module.system.impl.bootstrap;

import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.SyncServiceAccountResponse;
import com.omgservers.model.index.IndexConfigModel;
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

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StandaloneConfiguration {

    final SystemModule systemModule;

    final GetConfigOperation getConfigOperation;

    final IndexModelFactory indexModelFactory;
    final ServiceAccountModelFactory serviceAccountModelFactory;

    @WithSpan
    void startup(@Observes @Priority(2000) StartupEvent event) {
        if (getConfigOperation.getConfig().standalone()) {
            Uni.createFrom().voidItem()
                    .flatMap(voidItem -> syncIndex())
                    .flatMap(wasIndexCreated -> syncServiceAccount())
                    .await().indefinitely();
        } else {
            log.warn("Bootstrap of standalone configuration was skipped");
        }
    }

    Uni<Boolean> syncIndex() {
        final var indexName = getConfigOperation.getConfig().indexName();
        final var serverUri = getConfigOperation.getConfig().serverUri();
        final var indexConfig = IndexConfigModel.create(Collections.singletonList(serverUri));
        final var indexModel = indexModelFactory.create(indexName, indexConfig);
        final var request = new SyncIndexRequest(indexModel);
        return systemModule.getIndexService().syncIndex(request)
                .map(SyncIndexResponse::getCreated);
    }

    Uni<Boolean> syncServiceAccount() {
        final var serviceUsername = getConfigOperation.getConfig().serviceUsername();
        final var servicePassword = getConfigOperation.getConfig().servicePassword();
        final var passwordHash = BcryptUtil.bcryptHash(servicePassword);
        final var serviceAccountModel = serviceAccountModelFactory.create(serviceUsername, passwordHash);
        final var request = new SyncServiceAccountRequest(serviceAccountModel);
        return systemModule.getServiceAccountService().syncServiceAccount(request)
                .map(SyncServiceAccountResponse::getCreated);
    }
}
