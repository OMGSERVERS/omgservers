package com.omgservers.service.handler.impl.service;

import com.omgservers.schema.master.index.GetIndexRequest;
import com.omgservers.schema.master.index.GetIndexResponse;
import com.omgservers.schema.model.index.IndexConfigDto;
import com.omgservers.schema.model.index.IndexModel;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.system.IndexCreatedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.master.index.IndexMaster;
import com.omgservers.service.server.cache.CacheService;
import com.omgservers.service.server.cache.dto.CacheIndexConfigRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class IndexCreatedEventHandlerImpl implements EventHandler {

    final IndexMaster indexMaster;

    final CacheService cacheService;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.INDEX_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (IndexCreatedEventBodyModel) event.getBody();

        return getIndex()
                .flatMap(index -> {
                    log.debug("Created, {}", index);

                    final var indexConfig = index.getConfig();
                    return cacheIndexConfig(indexConfig);
                })
                .replaceWithVoid();
    }

    Uni<IndexModel> getIndex() {
        final var request = new GetIndexRequest();
        return indexMaster.getService().execute(request)
                .map(GetIndexResponse::getIndex);
    }

    Uni<Void> cacheIndexConfig(final IndexConfigDto indexConfig) {
        final var request = new CacheIndexConfigRequest(indexConfig);
        return cacheService.execute(request)
                .replaceWithVoid();
    }
}
