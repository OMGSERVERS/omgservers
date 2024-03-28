package com.omgservers.service.module.root.impl.service.webService.impl.api;

import com.omgservers.model.dto.root.DeleteRootRequest;
import com.omgservers.model.dto.root.DeleteRootResponse;
import com.omgservers.model.dto.root.GetRootRequest;
import com.omgservers.model.dto.root.GetRootResponse;
import com.omgservers.model.dto.root.SyncRootRequest;
import com.omgservers.model.dto.root.SyncRootResponse;
import com.omgservers.service.module.root.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RootApiImpl implements RootApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    public Uni<GetRootResponse> getRoot(final GetRootRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getRoot);
    }

    @Override
    public Uni<SyncRootResponse> syncRoot(final SyncRootRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncRoot);
    }

    @Override
    public Uni<DeleteRootResponse> deleteRoot(final DeleteRootRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRoot);
    }
}
