package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.collectLogsMethod;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CollectLogsHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CollectLogsHelpResponse;
import com.omgservers.application.module.internalModule.impl.operation.getInternalsServiceApiClientOperation.GetInternalsServiceApiClientOperation;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.ViewLogsHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.response.ViewLogsHelpResponse;
import com.omgservers.application.module.internalModule.model.log.LogModel;
import com.omgservers.application.operation.getServersOperation.GetServersOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
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
                .map(CollectLogsHelpResponse::new);
    }

    Uni<List<LogModel>> collectLogs(List<URI> servers) {
        log.info("Collect logs from servers, servers={}", servers);
        return Multi.createFrom().iterable(servers)
                .map(getInternalsServiceApiClientOperation::getClient)
                .onItem().transformToUniAndMerge(client -> {
                    final var request = new ViewLogsHelpRequest();
                    return client.viewLogs(request);
                })
                .collect().asList()
                .map(this::flatLogs);

    }

    List<LogModel> flatLogs(List<ViewLogsHelpResponse> responses) {
        return responses.stream()
                .flatMap(response -> response.getLogs().stream())
                .sorted(Comparator.comparingLong(log -> log.getCreated().toEpochMilli()))
                .toList();
    }
}
