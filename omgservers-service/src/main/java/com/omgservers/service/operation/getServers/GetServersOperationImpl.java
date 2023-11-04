package com.omgservers.service.operation.getServers;

import com.omgservers.model.dto.system.GetIndexRequest;
import com.omgservers.model.dto.system.GetIndexResponse;
import com.omgservers.model.index.IndexServerModel;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.calculateCrc16.CalculateCrc16Operation;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetServersOperationImpl implements GetServersOperation {

    final SystemModule systemModule;

    final CalculateCrc16Operation calculateCrc16Operation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<List<URI>> getServers() {
        final var indexName = getConfigOperation.getConfig().indexName();
        final var getIndexInternalRequest = new GetIndexRequest(indexName);
        return systemModule.getIndexService().getIndex(getIndexInternalRequest)
                .map(GetIndexResponse::getIndex)
                .map(index -> index.getConfig().getServers().stream()
                        .map(IndexServerModel::getUri)
                        .toList());
    }
}
