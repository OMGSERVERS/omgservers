package com.omgservers.service.module.worker.impl.service.webService.impl.api;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerRequest;
import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerRequest;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerResponse;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.module.worker.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
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
    public Uni<GetWorkerContextWorkerResponse> getWorkerContext(final GetWorkerContextWorkerRequest request) {
        return webService.getWorkerContext(request);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.WORKER})
    public Uni<DoWorkerCommandsWorkerResponse> doWorkerCommands(final DoWorkerCommandsWorkerRequest request) {
        return webService.doWorkerCommands(request);
    }
}
