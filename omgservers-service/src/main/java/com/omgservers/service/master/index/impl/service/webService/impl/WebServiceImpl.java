package com.omgservers.service.master.index.impl.service.webService.impl;

import com.omgservers.schema.master.index.GetIndexRequest;
import com.omgservers.schema.master.index.GetIndexResponse;
import com.omgservers.schema.master.index.SyncIndexRequest;
import com.omgservers.schema.master.index.SyncIndexResponse;
import com.omgservers.service.master.index.impl.service.indexService.IndexService;
import com.omgservers.service.master.index.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final IndexService indexService;

    @Override
    public Uni<GetIndexResponse> execute(final GetIndexRequest request) {
        return indexService.execute(request);
    }

    @Override
    public Uni<SyncIndexResponse> execute(final SyncIndexRequest request) {
        return indexService.execute(request);
    }
}
