package com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.impl.serviceApi;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.CreateRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DeleteRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.GetRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.DeleteRuntimeInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.GetRuntimeInternalResponse;
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
    public Uni<Void> createRuntime(CreateRuntimeInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, runtimeWebService::createRuntime);
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
}
