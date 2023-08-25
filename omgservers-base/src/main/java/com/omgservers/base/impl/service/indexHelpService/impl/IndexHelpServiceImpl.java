package com.omgservers.base.impl.service.indexHelpService.impl;

import com.omgservers.base.impl.service.indexHelpService.IndexHelpService;
import com.omgservers.base.impl.service.indexHelpService.impl.method.deleteIndexMethod.DeleteIndexMethod;
import com.omgservers.base.impl.service.indexHelpService.impl.method.getIndexMethod.GetIndexMethod;
import com.omgservers.base.impl.service.indexHelpService.impl.method.syncIndexMethod.SyncIndexMethod;
import com.omgservers.dto.internalModule.DeleteIndexHelpRequest;
import com.omgservers.dto.internalModule.GetIndexHelpRequest;
import com.omgservers.dto.internalModule.SyncIndexHelpRequest;
import com.omgservers.dto.internalModule.GetIndexHelpResponse;
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
