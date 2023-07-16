package com.omgservers.application.operation.getServersOperation;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.GetIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.response.GetIndexHelpResponse;
import com.omgservers.application.module.internalModule.model.index.IndexServerModel;
import com.omgservers.application.operation.calculateCrc16Operation.CalculateCrc16Operation;
import com.omgservers.application.operation.getConfigOperation.GetConfigOperation;
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
        final var getIndexInternalRequest = new GetIndexHelpRequest(indexName);
        return internalModule.getIndexInternalService().getIndex(getIndexInternalRequest)
                .map(GetIndexHelpResponse::getIndex)
                .map(index -> index.getConfig().getServers().stream()
                        .map(IndexServerModel::getUri)
                        .toList());
    }
}
