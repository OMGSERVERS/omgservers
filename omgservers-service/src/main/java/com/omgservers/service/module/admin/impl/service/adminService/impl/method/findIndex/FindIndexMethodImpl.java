package com.omgservers.service.module.admin.impl.service.adminService.impl.method.findIndex;

import com.omgservers.model.dto.admin.FindIndexAdminRequest;
import com.omgservers.model.dto.admin.FindIndexAdminResponse;
import com.omgservers.service.module.system.SystemModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindIndexMethodImpl implements FindIndexMethod {

    final SystemModule systemModule;

    @Override
    public Uni<FindIndexAdminResponse> findIndex(final FindIndexAdminRequest request) {
        log.debug("Find index, request={}", request);

        final var indexName = request.getName();
        return systemModule.getShortcutService().findIndex(indexName)
                .map(FindIndexAdminResponse::new);
    }
}
