package com.omgservers.application.module.versionModule.impl.service.versionWebService.impl;

import com.omgservers.application.module.versionModule.impl.service.versionInternalService.VersionInternalService;
import com.omgservers.application.module.versionModule.impl.service.versionWebService.VersionWebService;
import com.omgservers.dto.versionModule.DeleteVersionShardRequest;
import com.omgservers.dto.versionModule.DeleteVersionInternalResponse;
import com.omgservers.dto.versionModule.GetBytecodeShardRequest;
import com.omgservers.dto.versionModule.GetBytecodeInternalResponse;
import com.omgservers.dto.versionModule.GetStageConfigShardRequest;
import com.omgservers.dto.versionModule.GetStageConfigInternalResponse;
import com.omgservers.dto.versionModule.GetVersionShardRequest;
import com.omgservers.dto.versionModule.GetVersionInternalResponse;
import com.omgservers.dto.versionModule.SyncVersionShardRequest;
import com.omgservers.dto.versionModule.SyncVersionInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class VersionWebServiceImpl implements VersionWebService {

    final VersionInternalService versionInternalService;

    @Override
    public Uni<GetVersionInternalResponse> getVersion(GetVersionShardRequest request) {
        return versionInternalService.getVersion(request);
    }

    @Override
    public Uni<SyncVersionInternalResponse> syncVersion(SyncVersionShardRequest request) {
        return versionInternalService.syncVersion(request);
    }

    @Override
    public Uni<DeleteVersionInternalResponse> deleteVersion(DeleteVersionShardRequest request) {
        return versionInternalService.deleteVersion(request);
    }

    @Override
    public Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeShardRequest request) {
        return versionInternalService.getBytecode(request);
    }

    @Override
    public Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigShardRequest request) {
        return versionInternalService.getStageConfig(request);
    }
}
