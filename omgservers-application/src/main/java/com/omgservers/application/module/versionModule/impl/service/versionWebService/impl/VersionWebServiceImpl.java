package com.omgservers.application.module.versionModule.impl.service.versionWebService.impl;

import com.omgservers.application.module.versionModule.impl.service.versionInternalService.VersionInternalService;
import com.omgservers.application.module.versionModule.impl.service.versionWebService.VersionWebService;
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
    public Uni<GetVersionInternalResponse> getVersion(GetVersionRoutedRequest request) {
        return versionInternalService.getVersion(request);
    }

    @Override
    public Uni<SyncVersionInternalResponse> syncVersion(SyncVersionRoutedRequest request) {
        return versionInternalService.syncVersion(request);
    }

    @Override
    public Uni<DeleteVersionInternalResponse> deleteVersion(DeleteVersionRoutedRequest request) {
        return versionInternalService.deleteVersion(request);
    }

    @Override
    public Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeRoutedRequest request) {
        return versionInternalService.getBytecode(request);
    }

    @Override
    public Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigRoutedRequest request) {
        return versionInternalService.getStageConfig(request);
    }
}
