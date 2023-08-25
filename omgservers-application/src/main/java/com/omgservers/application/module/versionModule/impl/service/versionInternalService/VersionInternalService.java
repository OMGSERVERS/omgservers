package com.omgservers.application.module.versionModule.impl.service.versionInternalService;

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
import io.smallrye.mutiny.Uni;

public interface VersionInternalService {

    Uni<GetVersionInternalResponse> getVersion(GetVersionInternalRequest request);

    Uni<SyncVersionInternalResponse> syncVersion(SyncVersionInternalRequest request);

    Uni<DeleteVersionInternalResponse> deleteVersion(DeleteVersionInternalRequest request);

    Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeInternalRequest request);

    Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigInternalRequest request);
}
