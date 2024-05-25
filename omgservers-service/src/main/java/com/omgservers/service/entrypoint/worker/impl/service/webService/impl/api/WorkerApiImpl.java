package com.omgservers.service.entrypoint.worker.impl.service.webService.impl.api;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.InterchangeWorkerRequest;
import com.omgservers.model.dto.worker.InterchangeWorkerResponse;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.worker.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
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
    public Uni<CreateTokenWorkerResponse> createToken(final CreateTokenWorkerRequest request) {
        return webService.createToken(request);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.WORKER})
    public Uni<GetVersionWorkerResponse> getVersion(final GetVersionWorkerRequest request) {
        return webService.getVersion(request);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.WORKER})
    public Uni<InterchangeWorkerResponse> interchange(final InterchangeWorkerRequest request) {
        return webService.interchange(request);
    }
}
