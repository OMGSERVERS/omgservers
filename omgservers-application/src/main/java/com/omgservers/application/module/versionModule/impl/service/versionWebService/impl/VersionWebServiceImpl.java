package com.omgservers.application.module.versionModule.impl.service.versionWebService.impl;

import com.omgservers.application.module.versionModule.impl.service.versionInternalService.VersionInternalService;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.*;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.DeleteVersionInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.SyncVersionInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionWebService.VersionWebService;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetBytecodeInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetStageConfigInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetVersionInternalResponse;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class VersionWebServiceImpl implements VersionWebService {

    final VersionInternalService versionInternalService;

    @Override
    public Uni<GetVersionInternalResponse> getVersion(GetVersionInternalRequest request) {
        return versionInternalService.getVersion(request);
    }

    @Override
    public Uni<SyncVersionInternalResponse> syncVersion(SyncVersionInternalRequest request) {
        return versionInternalService.syncVersion(request);
    }

    @Override
    public Uni<DeleteVersionInternalResponse> deleteVersion(DeleteVersionInternalRequest request) {
        return versionInternalService.deleteVersion(request);
    }

    @Override
    public Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeInternalRequest request) {
        return versionInternalService.getBytecode(request);
    }

    @Override
    public Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigInternalRequest request) {
        return versionInternalService.getStageConfig(request);
    }
}
