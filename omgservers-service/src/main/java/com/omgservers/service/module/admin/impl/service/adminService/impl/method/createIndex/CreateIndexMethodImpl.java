package com.omgservers.service.module.admin.impl.service.adminService.impl.method.createIndex;

import com.omgservers.model.dto.admin.CreateIndexAdminRequest;
import com.omgservers.model.dto.admin.CreateIndexAdminResponse;
import com.omgservers.model.index.IndexConfigModel;
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
class CreateIndexMethodImpl implements CreateIndexMethod {

    final SystemModule systemModule;

    final GetConfigOperation getConfigOperation;

    final IndexModelFactory indexModelFactory;

    @Override
    public Uni<CreateIndexAdminResponse> createIndex(final CreateIndexAdminRequest request) {
        log.debug("Create index, request={}", request);

        final var indexName = getConfigOperation.getConfig().indexName();
        final var shardCount = getConfigOperation.getConfig().shardCount();
        final var addresses = request.getAddresses();

        final var indexConfig = IndexConfigModel.create(addresses, shardCount);
        final var index = indexModelFactory.create(indexName, indexConfig);

        return systemModule.getShortcutService().syncIndex(index)
                .replaceWith(new CreateIndexAdminResponse(index));
    }
}
