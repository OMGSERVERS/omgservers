package com.omgservers.application.module.versionModule.impl.service.versionInternalService;

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

public interface VersionInternalService {

    Uni<GetVersionInternalResponse> getVersion(GetVersionRoutedRequest request);

    Uni<SyncVersionInternalResponse> syncVersion(SyncVersionRoutedRequest request);

    Uni<DeleteVersionInternalResponse> deleteVersion(DeleteVersionRoutedRequest request);

    Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeRoutedRequest request);

    Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigRoutedRequest request);
}
