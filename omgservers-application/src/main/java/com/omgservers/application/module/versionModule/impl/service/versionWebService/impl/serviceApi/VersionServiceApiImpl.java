package com.omgservers.application.module.versionModule.impl.service.versionWebService.impl.serviceApi;

import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.*;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.DeleteVersionInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.SyncVersionInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionWebService.VersionWebService;
import com.omgservers.application.module.securityModule.model.InternalRoleEnum;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetBytecodeInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetStageConfigInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetVersionInternalResponse;
import com.omgservers.application.operation.handleApiRequestOperation.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionServiceApiImpl implements VersionServiceApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final VersionWebService versionWebService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetVersionInternalResponse> getVersion(GetVersionInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, versionWebService::getVersion);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncVersionInternalResponse> syncVersion(SyncVersionInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, versionWebService::syncVersion);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteVersionInternalResponse> deleteVersion(DeleteVersionInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, versionWebService::deleteVersion);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, versionWebService::getBytecode);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, versionWebService::getStageConfig);
    }
}
