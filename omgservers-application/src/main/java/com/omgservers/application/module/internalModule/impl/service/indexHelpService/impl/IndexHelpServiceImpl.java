package com.omgservers.application.module.internalModule.impl.service.indexHelpService.impl;

import com.omgservers.application.module.internalModule.impl.service.indexHelpService.IndexHelpService;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.impl.method.deleteIndexMethod.DeleteIndexMethod;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.impl.method.getIndexMethod.GetIndexMethod;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.impl.method.syncIndexMethod.SyncIndexMethod;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.DeleteIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.GetIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.SyncIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.response.GetIndexHelpResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class IndexHelpServiceImpl implements IndexHelpService {

    final GetIndexMethod getIndexMethod;
    final SyncIndexMethod syncIndexMethod;
    final DeleteIndexMethod deleteIndexMethod;

    @Override
    public Uni<GetIndexHelpResponse> getIndex(GetIndexHelpRequest request) {
        return getIndexMethod.getIndex(request);
    }

    @Override
    public Uni<Void> syncIndex(SyncIndexHelpRequest request) {
        return syncIndexMethod.syncIndex(request);
    }

    @Override
    public Uni<Void> deleteIndex(DeleteIndexHelpRequest request) {
        return deleteIndexMethod.deleteIndex(request);
    }
}
