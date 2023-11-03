package com.omgservers.worker.operation.getVersion;

import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.version.VersionModel;
import com.omgservers.worker.component.TokenHolder;
import com.omgservers.worker.module.service.ServiceModule;
import com.omgservers.worker.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class GetVersionOperationImpl implements GetVersionOperation {

    final ServiceModule serviceModule;

    final GetConfigOperation getConfigOperation;

    final TokenHolder tokenHolder;

    @Override
    public Uni<VersionModel> getVersion() {
        final var token = tokenHolder.getToken();
        return getVersion(token);
    }

    Uni<VersionModel> getVersion(final String token) {
        final var runtimeId = getConfigOperation.getConfig().runtimeId();
        final var request = new GetVersionWorkerRequest(runtimeId);
        return serviceModule.getWorkerService().getVersion(request, token)
                .map(GetVersionWorkerResponse::getVersion);
    }
}
