package com.omgservers.application.module.versionModule.impl.service.versionWebService.impl.serviceApi;

import com.omgservers.application.module.versionModule.impl.service.versionWebService.VersionWebService;
import com.omgservers.base.impl.operation.handleApiRequestOperation.HandleApiRequestOperation;
import com.omgservers.dto.versionModule.DeleteVersionInternalRequest;
import com.omgservers.dto.versionModule.DeleteVersionInternalResponse;
import com.omgservers.dto.versionModule.GetBytecodeInternalRequest;
import com.omgservers.dto.versionModule.GetBytecodeInternalResponse;
import com.omgservers.dto.versionModule.GetStageConfigInternalRequest;
import com.omgservers.dto.versionModule.GetStageConfigInternalResponse;
import com.omgservers.dto.versionModule.GetVersionInternalRequest;
import com.omgservers.dto.versionModule.GetVersionInternalResponse;
import com.omgservers.dto.versionModule.SyncVersionInternalRequest;
import com.omgservers.dto.versionModule.SyncVersionInternalResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
