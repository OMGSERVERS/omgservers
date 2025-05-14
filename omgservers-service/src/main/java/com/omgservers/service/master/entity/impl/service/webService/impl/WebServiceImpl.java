package com.omgservers.service.master.entity.impl.service.webService.impl;

import com.omgservers.schema.master.entity.DeleteEntityRequest;
import com.omgservers.schema.master.entity.DeleteEntityResponse;
import com.omgservers.schema.master.entity.FindEntityRequest;
import com.omgservers.schema.master.entity.FindEntityResponse;
import com.omgservers.schema.master.entity.GetEntityRequest;
import com.omgservers.schema.master.entity.GetEntityResponse;
import com.omgservers.schema.master.entity.SyncEntityRequest;
import com.omgservers.schema.master.entity.SyncEntityResponse;
import com.omgservers.schema.master.entity.ViewEntitiesRequest;
import com.omgservers.schema.master.entity.ViewEntitiesResponse;
import com.omgservers.service.master.entity.impl.service.entityService.EntityService;
import com.omgservers.service.master.entity.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final EntityService entityService;

    @Override
    public Uni<GetEntityResponse> execute(final GetEntityRequest request) {
        return entityService.execute(request);
    }

    @Override
    public Uni<FindEntityResponse> execute(final FindEntityRequest request) {
        return entityService.execute(request);
    }

    @Override
    public Uni<ViewEntitiesResponse> execute(final ViewEntitiesRequest request) {
        return entityService.execute(request);
    }

    @Override
    public Uni<SyncEntityResponse> execute(final SyncEntityRequest request) {
        return entityService.execute(request);
    }

    @Override
    public Uni<DeleteEntityResponse> execute(final DeleteEntityRequest request) {
        return entityService.execute(request);
    }
}
