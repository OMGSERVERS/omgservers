package com.omgservers.service.entrypoint.worker.impl.service.webService.impl.api;

import com.omgservers.schema.entrypoint.worker.CreateTokenWorkerRequest;
import com.omgservers.schema.entrypoint.worker.CreateTokenWorkerResponse;
import com.omgservers.schema.entrypoint.worker.GetConfigWorkerRequest;
import com.omgservers.schema.entrypoint.worker.GetConfigWorkerResponse;
import com.omgservers.schema.entrypoint.worker.InterchangeWorkerRequest;
import com.omgservers.schema.entrypoint.worker.InterchangeWorkerResponse;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.worker.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@RolesAllowed({UserRoleEnum.Names.WORKER})
class WorkerApiImpl implements WorkerApi {

    final WebService webService;

    @Override
    @PermitAll
    public Uni<CreateTokenWorkerResponse> createToken(@NotNull final CreateTokenWorkerRequest request) {
        return webService.createToken(request);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.WORKER})
    public Uni<GetConfigWorkerResponse> getConfig(@NotNull final GetConfigWorkerRequest request) {
        return webService.getConfig(request);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.WORKER})
    public Uni<InterchangeWorkerResponse> interchange(@NotNull final InterchangeWorkerRequest request) {
        return webService.interchange(request);
    }
}
