package com.omgservers.service.module.admin.impl.service.adminService.impl.method.findIndex;

import com.omgservers.model.dto.admin.FindIndexAdminRequest;
import com.omgservers.model.dto.admin.FindIndexAdminResponse;
import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.model.index.IndexModel;
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
        return findIndex(indexName)
                .map(FindIndexAdminResponse::new);
    }

    Uni<IndexModel> findIndex(final String indexName) {
        final var request = new FindIndexRequest(indexName);
        return systemModule.getIndexService().findIndex(request)
                .map(FindIndexResponse::getIndex);
    }
}
