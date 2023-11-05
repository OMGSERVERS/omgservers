package com.omgservers.service.module.tenant.impl.service.versionService;

import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRequest;
import com.omgservers.model.dto.tenant.DeleteVersionResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.FindStageVersionIdRequest;
import com.omgservers.model.dto.tenant.FindStageVersionIdResponse;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.FindVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.FindVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.GetVersionConfigRequest;
import com.omgservers.model.dto.tenant.GetVersionConfigResponse;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.model.dto.tenant.GetVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.GetVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.SelectVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SelectVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.SelectVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.SelectVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.SyncVersionRequest;
import com.omgservers.model.dto.tenant.SyncVersionResponse;
import com.omgservers.model.dto.tenant.SyncVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.SyncVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersResponse;
import com.omgservers.model.dto.tenant.ViewVersionRuntimesRequest;
import com.omgservers.model.dto.tenant.ViewVersionRuntimesResponse;
import com.omgservers.model.dto.tenant.ViewVersionsRequest;
import com.omgservers.model.dto.tenant.ViewVersionsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface VersionService {

    Uni<GetVersionResponse> getVersion(@Valid GetVersionRequest request);

    Uni<SyncVersionResponse> syncVersion(@Valid SyncVersionRequest request);

    Uni<ViewVersionsResponse> viewVersions(@Valid ViewVersionsRequest request);

    Uni<DeleteVersionResponse> deleteVersion(@Valid DeleteVersionRequest request);

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
