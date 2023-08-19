package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.collectLogsMethod;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CollectLogsHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CollectLogsHelpResponse;
import com.omgservers.application.module.adminModule.model.ServerLogModel;
import com.omgservers.application.module.internalModule.impl.operation.getInternalsServiceApiClientOperation.GetInternalsServiceApiClientOperation;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.ViewLogsHelpRequest;
import com.omgservers.application.operation.getServersOperation.GetServersOperation;
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
    public Uni<CollectLogsHelpResponse> collectLogs(CollectLogsHelpRequest request) {
        CollectLogsHelpRequest.validate(request);

        return getServersOperation.getServers()
                .flatMap(this::collectLogs)
                .map(collectedLogs -> new CollectLogsHelpResponse(collectedLogs.size(), collectedLogs));
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
