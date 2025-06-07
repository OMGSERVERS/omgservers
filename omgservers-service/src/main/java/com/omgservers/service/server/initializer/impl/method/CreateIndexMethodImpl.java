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
        final var shards = getServiceConfigOperation.getServiceConfig().index().shards();
        final var slotsCount = getServiceConfigOperation.getServiceConfig().index().slotsCount();
        log.info("Index not found, create a new one for \"{}\" shard/s with \"{}\" slots",
                shards.size(), slotsCount);

        final var indexConfig = IndexConfigDto.create(shards, slotsCount);
        final var index = indexModelFactory.create(indexConfig, "initialization");

        final var request = new SyncIndexRequest(index);
        return indexMaster.getService().executeWithIdempotency(request)
                .map(SyncIndexResponse::getCreated)
                .invoke(created -> {
                    if (created) {
                        log.info("Index created");
                    }
                })
                .replaceWithVoid();
    }
}
