package com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.impl.serviceApi;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DeleteActorInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DeleteCommandInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DeleteRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.GetRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.SyncActorInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.SyncCommandInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.SyncRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.DeleteActorInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.DeleteCommandInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.DeleteRuntimeInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.GetRuntimeInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.SyncActorInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.SyncCommandInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.SyncRuntimeInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.RuntimeWebService;
import com.omgservers.application.module.securityModule.model.InternalRoleEnum;
import com.omgservers.application.operation.handleApiRequestOperation.HandleApiRequestOperation;
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
    public Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::syncRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::getRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::deleteRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncActorInternalResponse> syncActor(SyncActorInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::syncActor);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteActorInternalResponse> deleteActor(DeleteActorInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::deleteActor);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncCommandInternalResponse> syncCommand(SyncCommandInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::syncCommand);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::deleteCommand);
    }
}
