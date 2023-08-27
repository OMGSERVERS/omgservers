package com.omgservers.operation.getServers;

import com.omgservers.module.internal.InternalModule;
import com.omgservers.operation.calculateCrc16.CalculateCrc16Operation;
import com.omgservers.operation.getConfig.GetConfigOperation;
import com.omgservers.dto.internal.GetIndexRequest;
import com.omgservers.dto.internal.GetIndexHelpResponse;
import com.omgservers.model.index.IndexServerModel;
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

    final InternalModule internalModule;

    final CalculateCrc16Operation calculateCrc16Operation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<List<URI>> getServers() {
        final var indexName = getConfigOperation.getConfig().indexName();
        final var getIndexInternalRequest = new GetIndexRequest(indexName);
        return internalModule.getIndexService().getIndex(getIndexInternalRequest)
                .map(GetIndexHelpResponse::getIndex)
                .map(index -> index.getConfig().getServers().stream()
                        .map(IndexServerModel::getUri)
                        .toList());
    }
}
