package com.omgservers.module.version.impl.service.versionWebService.impl;

import com.omgservers.module.version.impl.service.versionShardedService.VersionShardedService;
import com.omgservers.module.version.impl.service.versionWebService.VersionWebService;
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
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class VersionWebServiceImpl implements VersionWebService {

    final VersionShardedService versionShardedService;

    @Override
    public Uni<GetVersionShardedResponse> getVersion(GetVersionShardedRequest request) {
        return versionShardedService.getVersion(request);
    }

    @Override
    public Uni<SyncVersionShardedResponse> syncVersion(SyncVersionShardedRequest request) {
        return versionShardedService.syncVersion(request);
    }

    @Override
    public Uni<DeleteVersionShardedResponse> deleteVersion(DeleteVersionShardedRequest request) {
        return versionShardedService.deleteVersion(request);
    }

    @Override
    public Uni<GetBytecodeShardedResponse> getBytecode(GetBytecodeShardedRequest request) {
        return versionShardedService.getBytecode(request);
    }

    @Override
    public Uni<GetStageConfigShardedResponse> getStageConfig(GetStageConfigShardedRequest request) {
        return versionShardedService.getStageConfig(request);
    }
}
