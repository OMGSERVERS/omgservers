package com.omgservers.module.tenant.impl.service.versionService;

import com.omgservers.dto.tenant.BuildVersionRequest;
import com.omgservers.dto.tenant.BuildVersionResponse;
import com.omgservers.dto.tenant.DeleteVersionRequest;
import com.omgservers.dto.tenant.DeleteVersionResponse;
import com.omgservers.dto.tenant.GetStageVersionIdRequest;
import com.omgservers.dto.tenant.GetStageVersionIdResponse;
import com.omgservers.dto.tenant.GetVersionBytecodeRequest;
import com.omgservers.dto.tenant.GetVersionBytecodeResponse;
import com.omgservers.dto.tenant.GetVersionConfigRequest;
import com.omgservers.dto.tenant.GetVersionConfigResponse;
import com.omgservers.dto.tenant.GetVersionResponse;
import com.omgservers.dto.tenant.GetVersionRequest;
import com.omgservers.dto.tenant.SyncVersionRequest;
import com.omgservers.dto.tenant.SyncVersionResponse;
import io.smallrye.mutiny.Uni;

public interface VersionService {

    Uni<BuildVersionResponse> buildVersion(BuildVersionRequest request);

    Uni<GetVersionResponse> getVersion(GetVersionRequest request);

    Uni<SyncVersionResponse> syncVersion(SyncVersionRequest request);

    Uni<DeleteVersionResponse> deleteVersion(DeleteVersionRequest request);

    Uni<GetVersionBytecodeResponse> getVersionBytecode(GetVersionBytecodeRequest request);

    Uni<GetVersionConfigResponse> getVersionConfig(GetVersionConfigRequest request);

    Uni<GetStageVersionIdResponse> getStageVersionId(GetStageVersionIdRequest request);
}
