package com.omgservers.module.admin.impl.service.adminService.impl.method.collectLogs;

import com.omgservers.dto.admin.CollectLogsAdminRequest;
import com.omgservers.dto.admin.CollectLogsAdminResponse;
import com.omgservers.module.system.impl.operation.getInternalModuleClient.GetInternalModuleClientOperation;
import com.omgservers.dto.internal.ViewLogRequest;
import com.omgservers.operation.getServers.GetServersOperation;
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

    final GetInternalModuleClientOperation getInternalModuleClientOperation;
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
                    final var client = getInternalModuleClientOperation.getClient(server);
                    final var request = new ViewLogRequest();
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
