package com.omgservers.service.entrypoint.server.impl.service.webService.impl.serverApi;

import com.omgservers.schema.entrypoint.server.BcryptHashServerRequest;
import com.omgservers.schema.entrypoint.server.BcryptHashServerResponse;
import com.omgservers.schema.entrypoint.server.GenerateIdServerResponse;
import com.omgservers.schema.entrypoint.server.PingServerServerResponse;
import com.omgservers.service.entrypoint.server.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ServerApiImpl implements ServerApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    public Uni<PingServerServerResponse> pingServer() {
        return webService.pingServer();
    }

    @Override
    public Uni<GenerateIdServerResponse> generateId() {
        return webService.generateId();
    }

    @Override
    public Uni<BcryptHashServerResponse> execute(@NotNull final BcryptHashServerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
