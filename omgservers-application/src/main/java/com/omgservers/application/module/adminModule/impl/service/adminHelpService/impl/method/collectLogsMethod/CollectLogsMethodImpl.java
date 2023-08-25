package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.collectLogsMethod;

import com.omgservers.dto.adminModule.CollectLogsAdminRequest;
import com.omgservers.dto.adminModule.CollectLogsAdminResponse;
import com.omgservers.base.impl.operation.getInternalsServiceApiClientOperation.GetInternalsServiceApiClientOperation;
import com.omgservers.base.impl.service.logHelpService.request.ViewLogsHelpRequest;
import com.omgservers.base.impl.operation.getServersOperation.GetServersOperation;
import com.omgservers.model.serverLog.ServerLogModel;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CollectLogsMethodImpl implements CollectLogsMethod {

    final GetInternalsServiceApiClientOperation getInternalsServiceApiClientOperation;
    final GetServersOperation getServersOperation;

    @Override
    public Uni<CollectLogsAdminResponse> collectLogs(CollectLogsAdminRequest request) {
        CollectLogsAdminRequest.validate(request);

        return getServersOperation.getServers()
                .flatMap(this::collectLogs)
                .map(collectedLogs -> new CollectLogsAdminResponse(collectedLogs.size(), collectedLogs));
    }

    Uni<List<ServerLogModel>> collectLogs(List<URI> servers) {
        log.info("Collect logs from servers, servers={}", servers);
        return Multi.createFrom().iterable(servers)
                .onItem().transformToUniAndMerge(server -> {
                    final var client = getInternalsServiceApiClientOperation.getClient(server);
                    final var request = new ViewLogsHelpRequest();
                    return client.viewLogs(request)
                            .map(response -> response.getLogs().stream()
                                    .map(log -> new ServerLogModel(log.getId(),
                                            log.getCreated(),
                                            server,
                                            log.getMessage()))
                                    .toList());
                })
                .collect().asList()
                .map(this::flatLogs);

    }

    List<ServerLogModel> flatLogs(List<List<ServerLogModel>> serverLogs) {
        return serverLogs.stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparingLong(ServerLogModel::getId).reversed())
                .toList();
    }
}
