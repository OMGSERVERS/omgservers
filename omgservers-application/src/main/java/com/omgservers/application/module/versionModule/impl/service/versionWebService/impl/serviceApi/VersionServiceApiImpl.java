package com.omgservers.application.module.versionModule.impl.service.versionWebService.impl.serviceApi;

import com.omgservers.application.module.versionModule.impl.service.versionWebService.VersionWebService;
import com.omgservers.base.operation.handleApiRequest.HandleApiRequestOperation;
import com.omgservers.dto.versionModule.DeleteVersionRoutedRequest;
import com.omgservers.dto.versionModule.DeleteVersionInternalResponse;
import com.omgservers.dto.versionModule.GetBytecodeRoutedRequest;
import com.omgservers.dto.versionModule.GetBytecodeInternalResponse;
import com.omgservers.dto.versionModule.GetStageConfigRoutedRequest;
import com.omgservers.dto.versionModule.GetStageConfigInternalResponse;
import com.omgservers.dto.versionModule.GetVersionRoutedRequest;
import com.omgservers.dto.versionModule.GetVersionInternalResponse;
import com.omgservers.dto.versionModule.SyncVersionRoutedRequest;
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
    public Uni<GetVersionInternalResponse> getVersion(GetVersionRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, versionWebService::getVersion);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncVersionInternalResponse> syncVersion(SyncVersionRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, versionWebService::syncVersion);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteVersionInternalResponse> deleteVersion(DeleteVersionRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, versionWebService::deleteVersion);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, versionWebService::getBytecode);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, versionWebService::getStageConfig);
    }
}
