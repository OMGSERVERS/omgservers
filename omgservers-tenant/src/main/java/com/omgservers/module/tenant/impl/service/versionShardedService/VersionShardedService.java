package com.omgservers.module.tenant.impl.service.versionShardedService;

import com.omgservers.dto.tenant.DeleteVersionShardedRequest;
import com.omgservers.dto.tenant.DeleteVersionShardedResponse;
import com.omgservers.dto.tenant.GetCurrentVersionIdShardedRequest;
import com.omgservers.dto.tenant.GetCurrentVersionIdShardedResponse;
import com.omgservers.dto.tenant.GetVersionBytecodeShardedRequest;
import com.omgservers.dto.tenant.GetVersionBytecodeShardedResponse;
import com.omgservers.dto.tenant.GetVersionConfigShardedRequest;
import com.omgservers.dto.tenant.GetVersionConfigShardedResponse;
import com.omgservers.dto.tenant.GetVersionShardedRequest;
import com.omgservers.dto.tenant.GetVersionShardedResponse;
import com.omgservers.dto.tenant.SyncVersionShardedRequest;
import com.omgservers.dto.tenant.SyncVersionShardedResponse;
import io.smallrye.mutiny.Uni;

public interface VersionShardedService {

    Uni<GetVersionShardedResponse> getVersion(GetVersionShardedRequest request);

    Uni<SyncVersionShardedResponse> syncVersion(SyncVersionShardedRequest request);

    Uni<DeleteVersionShardedResponse> deleteVersion(DeleteVersionShardedRequest request);

    Uni<GetVersionBytecodeShardedResponse> getVersionBytecode(GetVersionBytecodeShardedRequest request);

    Uni<GetVersionConfigShardedResponse> getVersionConfig(GetVersionConfigShardedRequest request);

    Uni<GetCurrentVersionIdShardedResponse> getCurrentVersionId(GetCurrentVersionIdShardedRequest request);
}
