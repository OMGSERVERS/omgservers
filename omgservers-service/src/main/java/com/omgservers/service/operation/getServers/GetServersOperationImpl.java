package com.omgservers.service.operation.getServers;

import com.omgservers.schema.model.index.IndexModel;
import com.omgservers.schema.model.index.IndexServerDto;
import com.omgservers.service.operation.calculateCrc16.CalculateCrc16Operation;
import com.omgservers.service.operation.getServiceConfig.GetServiceConfigOperation;
import com.omgservers.service.service.index.IndexService;
import com.omgservers.service.service.index.dto.GetIndexRequest;
import com.omgservers.service.service.index.dto.GetIndexResponse;
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
    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public Uni<List<URI>> getServers() {
        return getIndex()
                .map(index -> index.getConfig().getServers().stream()
                        .map(IndexServerDto::getUri)
                        .toList());
    }

    Uni<IndexModel> getIndex() {
        final var request = new GetIndexRequest();
        return indexService.getIndex(request)
                .map(GetIndexResponse::getIndex);
    }
}
