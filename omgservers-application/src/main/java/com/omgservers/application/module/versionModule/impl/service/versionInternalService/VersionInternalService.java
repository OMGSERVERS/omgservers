package com.omgservers.application.module.versionModule.impl.service.versionInternalService;

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

public interface VersionInternalService {

    Uni<GetVersionInternalResponse> getVersion(GetVersionShardRequest request);

    Uni<SyncVersionInternalResponse> syncVersion(SyncVersionShardRequest request);

    Uni<DeleteVersionInternalResponse> deleteVersion(DeleteVersionShardRequest request);

    Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeShardRequest request);

    Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigShardRequest request);
}
