package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.schema.master.index.GetIndexRequest;
import com.omgservers.schema.master.index.GetIndexResponse;
import com.omgservers.schema.master.index.SyncIndexRequest;
import com.omgservers.schema.master.index.SyncIndexResponse;
import com.omgservers.schema.model.index.IndexConfigDto;
import com.omgservers.schema.model.index.IndexModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.system.IndexModelFactory;
import com.omgservers.service.master.index.IndexMaster;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateIndexMethodImpl implements CreateIndexMethod {

    final IndexMaster indexMaster;

    final GetServiceConfigOperation getServiceConfigOperation;

    final IndexModelFactory indexModelFactory;

    @Override
    public Uni<Void> execute() {
        log.debug("Create index");

        return getIndex()
                .invoke(index -> log.info("Index already created, skip operation"))
                .replaceWithVoid()
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> createIndex());
    }

    Uni<IndexModel> getIndex() {
        final var request = new GetIndexRequest();
        return indexMaster.getService().execute(request)
                .map(GetIndexResponse::getIndex);
    }

    Uni<Void> createIndex() {
        final var servers = getServiceConfigOperation.getServiceConfig().initialization()
                .serverIndex().servers();
        final var shardCount = getServiceConfigOperation.getServiceConfig().server().shardCount();
        log.info("Index not found, create a new one for {} server/s, shardCount={}",
                servers.size(), shardCount);

        final var indexConfig = IndexConfigDto.create(servers, shardCount);
        final var index = indexModelFactory.create(indexConfig, "initialization");

        final var request = new SyncIndexRequest(index);
        return indexMaster.getService().execute(request)
                .map(SyncIndexResponse::getCreated)
                .invoke(created -> log.info("Index created"))
                .replaceWithVoid();
    }
}
