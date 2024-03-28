package com.omgservers.service.module.root.impl.service.webService.impl;

import com.omgservers.model.dto.root.DeleteRootRequest;
import com.omgservers.model.dto.root.DeleteRootResponse;
import com.omgservers.model.dto.root.GetRootRequest;
import com.omgservers.model.dto.root.GetRootResponse;
import com.omgservers.model.dto.root.SyncRootRequest;
import com.omgservers.model.dto.root.SyncRootResponse;
import com.omgservers.service.module.root.impl.service.rootService.RootService;
import com.omgservers.service.module.root.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final RootService rootService;

    @Override
    public Uni<GetRootResponse> getRoot(final GetRootRequest request) {
        return rootService.getRoot(request);
    }

    @Override
    public Uni<SyncRootResponse> syncRoot(final SyncRootRequest request) {
        return rootService.syncRoot(request);
    }

    @Override
    public Uni<DeleteRootResponse> deleteRoot(final DeleteRootRequest request) {
        return rootService.deleteRoot(request);
    }
}
