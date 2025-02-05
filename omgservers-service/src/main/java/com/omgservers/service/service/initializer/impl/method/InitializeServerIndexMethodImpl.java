package com.omgservers.service.service.initializer.impl.method;

import com.omgservers.schema.model.index.IndexConfigDto;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.system.IndexModelFactory;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.service.index.IndexService;
import com.omgservers.service.service.index.dto.GetIndexRequest;
import com.omgservers.service.service.index.dto.GetIndexResponse;
import com.omgservers.service.service.index.dto.SyncIndexRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InitializeServerIndexMethodImpl implements InitializeServerIndexMethod {

    final IndexService indexService;

    final GetServiceConfigOperation getServiceConfigOperation;

    final IndexModelFactory indexModelFactory;

    @Override
    public Uni<Void> execute() {
        log.debug("Initialize server index");

        return indexService.getIndex(new GetIndexRequest())
                .map(GetIndexResponse::getIndex)
                .invoke(index -> log.info("Index was already created, skip operation"))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    final var servers = getServiceConfigOperation.getServiceConfig().initialization()
                            .serverIndex().servers();
                    final var shardCount = getServiceConfigOperation.getServiceConfig().server().shardCount();
                    log.info("Index was not found, create a new one for {} server/s, shardCount={}",
                            servers.size(), shardCount);

                    final var indexConfig = IndexConfigDto.create(servers, shardCount);
                    final var index = indexModelFactory.create(indexConfig, "index");

                    final var request = new SyncIndexRequest(index);
                    return indexService.syncIndexWithIdempotency(request)
                            .replaceWith(index);
                })
                .replaceWithVoid();
    }
}
