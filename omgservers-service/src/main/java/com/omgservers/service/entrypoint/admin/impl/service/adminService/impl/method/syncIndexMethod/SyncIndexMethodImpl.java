package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.syncIndexMethod;

import com.omgservers.model.dto.admin.SyncIndexAdminRequest;
import com.omgservers.model.dto.admin.SyncIndexAdminResponse;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import com.omgservers.model.index.IndexModel;
import com.omgservers.service.factory.system.IndexModelFactory;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncIndexMethodImpl implements SyncIndexMethod {

    final SystemModule systemModule;

    final GetConfigOperation getConfigOperation;

    final IndexModelFactory indexModelFactory;

    @Override
    public Uni<SyncIndexAdminResponse> syncIndex(final SyncIndexAdminRequest request) {
        log.debug("Sync index, request={}", request);

        final var index = request.getIndex();

        return syncIndex(index)
                .map(SyncIndexAdminResponse::new);
    }

    Uni<Boolean> syncIndex(final IndexModel index) {
        final var request = new SyncIndexRequest(index);
        return systemModule.getIndexService().syncIndex(request)
                .map(SyncIndexResponse::getCreated);
    }
}
