package com.omgservers.module.tenant.impl.service.versionService;

import com.omgservers.dto.tenant.BuildVersionRequest;
import com.omgservers.dto.tenant.BuildVersionResponse;
import com.omgservers.dto.tenant.DeleteVersionMatchmakerRequest;
import com.omgservers.dto.tenant.DeleteVersionMatchmakerResponse;
import com.omgservers.dto.tenant.DeleteVersionRequest;
import com.omgservers.dto.tenant.DeleteVersionResponse;
import com.omgservers.dto.tenant.DeleteVersionRuntimeRequest;
import com.omgservers.dto.tenant.DeleteVersionRuntimeResponse;
import com.omgservers.dto.tenant.FindStageVersionIdRequest;
import com.omgservers.dto.tenant.FindStageVersionIdResponse;
import com.omgservers.dto.tenant.FindVersionMatchmakerRequest;
import com.omgservers.dto.tenant.FindVersionMatchmakerResponse;
import com.omgservers.dto.tenant.FindVersionRuntimeRequest;
import com.omgservers.dto.tenant.FindVersionRuntimeResponse;
import com.omgservers.dto.tenant.GetVersionBytecodeRequest;
import com.omgservers.dto.tenant.GetVersionBytecodeResponse;
import com.omgservers.dto.tenant.GetVersionConfigRequest;
import com.omgservers.dto.tenant.GetVersionConfigResponse;
import com.omgservers.dto.tenant.GetVersionMatchmakerRequest;
import com.omgservers.dto.tenant.GetVersionMatchmakerResponse;
import com.omgservers.dto.tenant.GetVersionRequest;
import com.omgservers.dto.tenant.GetVersionResponse;
import com.omgservers.dto.tenant.GetVersionRuntimeRequest;
import com.omgservers.dto.tenant.GetVersionRuntimeResponse;
import com.omgservers.dto.tenant.SelectVersionMatchmakerRequest;
import com.omgservers.dto.tenant.SelectVersionMatchmakerResponse;
import com.omgservers.dto.tenant.SelectVersionRuntimeRequest;
import com.omgservers.dto.tenant.SelectVersionRuntimeResponse;
import com.omgservers.dto.tenant.SyncVersionMatchmakerRequest;
import com.omgservers.dto.tenant.SyncVersionMatchmakerResponse;
import com.omgservers.dto.tenant.SyncVersionRequest;
import com.omgservers.dto.tenant.SyncVersionResponse;
import com.omgservers.dto.tenant.SyncVersionRuntimeRequest;
import com.omgservers.dto.tenant.SyncVersionRuntimeResponse;
import com.omgservers.dto.tenant.ViewVersionMatchmakersRequest;
import com.omgservers.dto.tenant.ViewVersionMatchmakersResponse;
import com.omgservers.dto.tenant.ViewVersionRuntimesRequest;
import com.omgservers.dto.tenant.ViewVersionRuntimesResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface VersionService {

    Uni<BuildVersionResponse> buildVersion(@Valid BuildVersionRequest request);

    Uni<GetVersionResponse> getVersion(@Valid GetVersionRequest request);

    Uni<SyncVersionResponse> syncVersion(@Valid SyncVersionRequest request);

    Uni<DeleteVersionResponse> deleteVersion(@Valid DeleteVersionRequest request);

    Uni<GetVersionBytecodeResponse> getVersionBytecode(@Valid GetVersionBytecodeRequest request);

    Uni<GetVersionConfigResponse> getVersionConfig(@Valid GetVersionConfigRequest request);

    Uni<GetVersionMatchmakerResponse> getVersionMatchmaker(@Valid GetVersionMatchmakerRequest request);

    Uni<SyncVersionMatchmakerResponse> syncVersionMatchmaker(@Valid SyncVersionMatchmakerRequest request);

    Uni<FindVersionMatchmakerResponse> findVersionMatchmaker(@Valid FindVersionMatchmakerRequest request);

    Uni<SelectVersionMatchmakerResponse> selectVersionMatchmaker(@Valid SelectVersionMatchmakerRequest request);

    Uni<ViewVersionMatchmakersResponse> viewVersionMatchmakers(@Valid ViewVersionMatchmakersRequest request);

    Uni<DeleteVersionMatchmakerResponse> deleteVersionMatchmaker(@Valid DeleteVersionMatchmakerRequest request);

    Uni<GetVersionRuntimeResponse> getVersionRuntime(@Valid GetVersionRuntimeRequest request);

    Uni<SyncVersionRuntimeResponse> syncVersionRuntime(@Valid SyncVersionRuntimeRequest request);

    Uni<FindVersionRuntimeResponse> findVersionRuntime(@Valid FindVersionRuntimeRequest request);

    Uni<SelectVersionRuntimeResponse> selectVersionRuntime(@Valid SelectVersionRuntimeRequest request);

    Uni<ViewVersionRuntimesResponse> viewVersionRuntimes(@Valid ViewVersionRuntimesRequest request);

    Uni<DeleteVersionRuntimeResponse> deleteVersionRuntime(@Valid DeleteVersionRuntimeRequest request);

    Uni<FindStageVersionIdResponse> findStageVersionId(@Valid FindStageVersionIdRequest request);
}
