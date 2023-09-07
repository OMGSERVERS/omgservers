package com.omgservers.module.runtime.impl.service.webService.impl.api;

import com.omgservers.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.dto.runtime.DoRuntimeUpdateRequest;
import com.omgservers.dto.runtime.DoRuntimeUpdateResponse;
import com.omgservers.dto.runtime.GetRuntimeRequest;
import com.omgservers.dto.runtime.GetRuntimeResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.dto.runtime.SyncRuntimeRequest;
import com.omgservers.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.module.runtime.impl.service.webService.WebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeApiImpl implements RuntimeApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncRuntimeResponse> syncRuntime(SyncRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetRuntimeResponse> getRuntime(GetRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteRuntimeResponse> deleteRuntime(DeleteRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(SyncRuntimeCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncRuntimeCommand);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(DeleteRuntimeCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRuntimeCommand);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DoRuntimeUpdateResponse> doRuntimeUpdate(DoRuntimeUpdateRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::doRuntimeUpdate);
    }
}
