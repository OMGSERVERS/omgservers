package com.omgservers.service.entrypoint.admin.impl.service.webService.impl.adminApi;

import com.omgservers.model.dto.admin.BcryptHashAdminRequest;
import com.omgservers.model.dto.admin.BcryptHashAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import com.omgservers.model.dto.server.BootstrapIndexServerRequest;
import com.omgservers.model.dto.server.BootstrapIndexServerResponse;
import com.omgservers.service.entrypoint.admin.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class AdminApiImpl implements AdminApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    public Uni<PingServerAdminResponse> pingServer() {
        return webService.pingServer();
    }

    @Override
    public Uni<GenerateIdAdminResponse> generateId() {
        return webService.generateId();
    }

    @Override
    public Uni<BcryptHashAdminResponse> bcryptHash(final BcryptHashAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::bcryptHash);
    }

    @Override
    public Uni<BootstrapIndexServerResponse> bootstrapIndex(BootstrapIndexServerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::bootstrapIndex);
    }
}
