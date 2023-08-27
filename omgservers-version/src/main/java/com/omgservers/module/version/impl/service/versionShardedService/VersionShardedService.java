package com.omgservers.module.version.impl.service.versionShardedService;

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

public interface VersionShardedService {

    Uni<GetVersionShardedResponse> getVersion(GetVersionShardedRequest request);

    Uni<SyncVersionShardedResponse> syncVersion(SyncVersionShardedRequest request);

    Uni<DeleteVersionShardedResponse> deleteVersion(DeleteVersionShardedRequest request);

    Uni<GetBytecodeShardedResponse> getBytecode(GetBytecodeShardedRequest request);

    Uni<GetStageConfigShardedResponse> getStageConfig(GetStageConfigShardedRequest request);
}
