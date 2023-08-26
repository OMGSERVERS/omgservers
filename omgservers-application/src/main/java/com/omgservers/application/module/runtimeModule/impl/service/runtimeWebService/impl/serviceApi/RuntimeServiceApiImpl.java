package com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.impl.serviceApi;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.RuntimeWebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import com.omgservers.dto.runtimeModule.DeleteCommandShardRequest;
import com.omgservers.dto.runtimeModule.DeleteCommandInternalResponse;
import com.omgservers.dto.runtimeModule.DeleteRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.DoUpdateShardRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.SyncCommandShardRequest;
import com.omgservers.dto.runtimeModule.SyncCommandInternalResponse;
import com.omgservers.dto.runtimeModule.SyncRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.SyncRuntimeInternalResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeServiceApiImpl implements RuntimeServiceApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final RuntimeWebService runtimeWebService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::syncRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::getRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::deleteRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncCommandInternalResponse> syncCommand(SyncCommandShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::syncCommand);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::deleteCommand);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> doUpdate(DoUpdateShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::doUpdate);
    }
}
