package com.omgservers.base.impl.operation.getServersOperation;

import com.omgservers.base.impl.operation.calculateCrc16Operation.CalculateCrc16Operation;
import com.omgservers.base.impl.operation.getConfigOperation.GetConfigOperation;
import com.omgservers.base.impl.service.indexHelpService.IndexHelpService;
import com.omgservers.dto.internalModule.GetIndexHelpRequest;
import com.omgservers.dto.internalModule.GetIndexHelpResponse;
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

    final IndexHelpService indexHelpService;

    final CalculateCrc16Operation calculateCrc16Operation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<List<URI>> getServers() {
        final var indexName = getConfigOperation.getConfig().indexName();
        final var getIndexInternalRequest = new GetIndexHelpRequest(indexName);
        return indexHelpService.getIndex(getIndexInternalRequest)
                .map(GetIndexHelpResponse::getIndex)
                .map(index -> index.getConfig().getServers().stream()
                        .map(IndexServerModel::getUri)
                        .toList());
    }
}
