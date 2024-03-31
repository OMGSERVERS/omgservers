package com.omgservers.service.bootstrap;

import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import com.omgservers.model.event.body.internal.RootInitializationRequestedEventBodyModel;
import com.omgservers.model.index.IndexConfigModel;
import com.omgservers.model.index.IndexModel;
import com.omgservers.service.configuration.ServicePriorityConfiguration;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.system.IndexModelFactory;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.opentelemetry.instrumentation.annotations.WithSpan;
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
public class BootstrapInstance {

    final SystemModule systemModule;

    final GetConfigOperation getConfigOperation;

    final IndexModelFactory indexModelFactory;
    final EventModelFactory eventModelFactory;

    @WithSpan
    void startup(@Observes @Priority(ServicePriorityConfiguration.START_UP_BOOTSTRAP_INSTANCE_PRIORITY)
                 final StartupEvent event) {
        Uni.createFrom().voidItem()
                .flatMap(voidItem -> createIndex())
                .flatMap(created -> requestRootInitialization())
                .await().indefinitely();
    }

    Uni<Void> createIndex() {
        if (getConfigOperation.getServiceConfig().bootstrap().index().enabled()) {
            final var indexName = getConfigOperation.getServiceConfig().index().name();
            return findIndex(indexName)
                    .onFailure(ServerSideNotFoundException.class)
                    .recoverWithUni(t -> {
                        final var servers = getConfigOperation.getServiceConfig().bootstrap().index().servers();
                        final var shardCount = getConfigOperation.getServiceConfig().index().shardCount();
                        final var indexConfig = IndexConfigModel.create(servers, shardCount);
                        final var indexModel = indexModelFactory.create(indexName, indexConfig);

                        log.info("Bootstrap index, name={}, servers={}, shards={}",
                                indexName,
                                servers.size(),
                                shardCount);

                        return syncIndex(indexModel)
                                .replaceWith(indexModel);
                    })
                    .replaceWithVoid();
        } else {
            return Uni.createFrom().voidItem();
        }
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

    Uni<Boolean> requestRootInitialization() {
        final var rootId = getConfigOperation.getServiceConfig().bootstrap().rootId();
        final var eventBody = new RootInitializationRequestedEventBodyModel(rootId);
        final var eventModel = eventModelFactory.create(eventBody);

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEvent(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }
}
