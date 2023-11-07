package com.omgservers.service.module.worker.impl.service.webService.impl.api;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.GetRuntimeStateWorkerRequest;
import com.omgservers.model.dto.worker.GetRuntimeStateWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerResponse;
import com.omgservers.model.dto.worker.UpdateRuntimeStateWorkerRequest;
import com.omgservers.model.dto.worker.UpdateRuntimeStateWorkerResponse;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerResponse;
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
    @RolesAllowed({UserRoleEnum.Names.CONTAINER})
    public Uni<GetVersionWorkerResponse> getVersion(final GetVersionWorkerRequest request) {
        return webService.getVersion(request);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.CONTAINER})
    public Uni<ViewRuntimeCommandsWorkerResponse> viewRuntimeCommands(final ViewRuntimeCommandsWorkerRequest request) {
        return webService.viewRuntimeCommands(request);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.CONTAINER})
    public Uni<HandleRuntimeCommandsWorkerResponse> handleRuntimeCommands(
            final HandleRuntimeCommandsWorkerRequest request) {
        return webService.handleRuntimeCommands(request);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.CONTAINER})
    public Uni<GetRuntimeStateWorkerResponse> getRuntimeState(final GetRuntimeStateWorkerRequest request) {
        return webService.getRuntimeState(request);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.CONTAINER})
    public Uni<UpdateRuntimeStateWorkerResponse> updateRuntimeState(final UpdateRuntimeStateWorkerRequest request) {
        return webService.updateRuntimeState(request);
    }
}
