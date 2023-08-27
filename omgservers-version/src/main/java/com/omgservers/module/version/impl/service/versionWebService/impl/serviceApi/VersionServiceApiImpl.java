package com.omgservers.module.version.impl.service.versionWebService.impl.serviceApi;

import com.omgservers.module.version.impl.service.versionWebService.VersionWebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import com.omgservers.dto.version.DeleteVersionShardedRequest;
import com.omgservers.dto.version.DeleteVersionShardedResponse;
import com.omgservers.dto.version.GetBytecodeShardedRequest;
import com.omgservers.dto.version.GetBytecodeShardedResponse;
import com.omgservers.dto.version.GetStageConfigShardedRequest;
import com.omgservers.dto.version.GetStageConfigShardedResponse;
import com.omgservers.dto.version.GetVersionShardedRequest;
import com.omgservers.dto.version.GetVersionShardedResponse;
import com.omgservers.dto.version.SyncVersionShardedRequest;
import com.omgservers.dto.version.SyncVersionShardedResponse;
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
    public Uni<GetVersionShardedResponse> getVersion(GetVersionShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, versionWebService::getVersion);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncVersionShardedResponse> syncVersion(SyncVersionShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, versionWebService::syncVersion);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteVersionShardedResponse> deleteVersion(DeleteVersionShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, versionWebService::deleteVersion);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetBytecodeShardedResponse> getBytecode(GetBytecodeShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, versionWebService::getBytecode);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetStageConfigShardedResponse> getStageConfig(GetStageConfigShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, versionWebService::getStageConfig);
    }
}
