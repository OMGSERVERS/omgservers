package com.omgservers.service.module.admin.impl.service.adminService.impl.method.syncIndexMethod;

import com.omgservers.model.dto.admin.SyncIndexAdminRequest;
import com.omgservers.model.dto.admin.SyncIndexAdminResponse;
import com.omgservers.service.factory.IndexModelFactory;
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

        return systemModule.getShortcutService().syncIndex(index)
                .map(SyncIndexAdminResponse::new);
    }
}
