package com.omgservers.service.operation.getServers;

import com.omgservers.schema.model.index.IndexModel;
import com.omgservers.schema.model.index.IndexServerDto;
import com.omgservers.service.service.index.dto.GetIndexRequest;
import com.omgservers.service.service.index.dto.GetIndexResponse;
import com.omgservers.service.operation.calculateCrc16.CalculateCrc16Operation;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.service.index.IndexService;
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

    final IndexService indexService;

    final CalculateCrc16Operation calculateCrc16Operation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<List<URI>> getServers() {
        final var indexId = getConfigOperation.getServiceConfig().defaults().indexId();
        return getIndex(indexId)
                .map(index -> index.getConfig().getServers().stream()
                        .map(IndexServerDto::getUri)
                        .toList());
    }

    Uni<IndexModel> getIndex(final Long indexId) {
        final var request = new GetIndexRequest(indexId);
        return indexService.getIndex(request)
                .map(GetIndexResponse::getIndex);
    }
}
