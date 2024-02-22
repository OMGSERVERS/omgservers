package com.omgservers.service.module.tenant.impl.service.versionService;

import com.omgservers.model.dto.tenant.DeleteVersionLobbyRefRequest;
import com.omgservers.model.dto.tenant.DeleteVersionLobbyRefResponse;
import com.omgservers.model.dto.tenant.DeleteVersionLobbyRequestRequest;
import com.omgservers.model.dto.tenant.DeleteVersionLobbyRequestResponse;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRefResponse;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRequestResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRequest;
import com.omgservers.model.dto.tenant.DeleteVersionResponse;
import com.omgservers.model.dto.tenant.FindVersionLobbyRefRequest;
import com.omgservers.model.dto.tenant.FindVersionLobbyRefResponse;
import com.omgservers.model.dto.tenant.FindVersionLobbyRequestRequest;
import com.omgservers.model.dto.tenant.FindVersionLobbyRequestResponse;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRefResponse;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequestResponse;
import com.omgservers.model.dto.tenant.GetVersionConfigRequest;
import com.omgservers.model.dto.tenant.GetVersionConfigResponse;
import com.omgservers.model.dto.tenant.GetVersionLobbyRefRequest;
import com.omgservers.model.dto.tenant.GetVersionLobbyRefResponse;
import com.omgservers.model.dto.tenant.GetVersionLobbyRequestRequest;
import com.omgservers.model.dto.tenant.GetVersionLobbyRequestResponse;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRefResponse;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequestResponse;
import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.model.dto.tenant.SelectStageVersionRequest;
import com.omgservers.model.dto.tenant.SelectStageVersionResponse;
import com.omgservers.model.dto.tenant.SyncVersionLobbyRefRequest;
import com.omgservers.model.dto.tenant.SyncVersionLobbyRefResponse;
import com.omgservers.model.dto.tenant.SyncVersionLobbyRequestRequest;
import com.omgservers.model.dto.tenant.SyncVersionLobbyRequestResponse;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRefResponse;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequestResponse;
import com.omgservers.model.dto.tenant.SyncVersionRequest;
import com.omgservers.model.dto.tenant.SyncVersionResponse;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRefsRequest;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRefsResponse;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRequestsRequest;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRequestsResponse;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRefsRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRefsResponse;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRequestsRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRequestsResponse;
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

    Uni<SelectStageVersionResponse> selectStageVersion(@Valid SelectStageVersionRequest request);

    Uni<GetVersionLobbyRequestResponse> getVersionLobbyRequest(@Valid GetVersionLobbyRequestRequest request);

    Uni<FindVersionLobbyRequestResponse> findVersionLobbyRequest(@Valid FindVersionLobbyRequestRequest request);

    Uni<ViewVersionLobbyRequestsResponse> viewVersionLobbyRequests(@Valid ViewVersionLobbyRequestsRequest request);

    Uni<SyncVersionLobbyRequestResponse> syncVersionLobbyRequest(@Valid SyncVersionLobbyRequestRequest request);

    Uni<DeleteVersionLobbyRequestResponse> deleteVersionLobbyRequest(@Valid DeleteVersionLobbyRequestRequest request);

    Uni<GetVersionLobbyRefResponse> getVersionLobbyRef(@Valid GetVersionLobbyRefRequest request);

    Uni<FindVersionLobbyRefResponse> findVersionLobbyRef(@Valid FindVersionLobbyRefRequest request);

    Uni<ViewVersionLobbyRefsResponse> viewVersionLobbyRefs(@Valid ViewVersionLobbyRefsRequest request);

    Uni<SyncVersionLobbyRefResponse> syncVersionLobbyRef(@Valid SyncVersionLobbyRefRequest request);

    Uni<DeleteVersionLobbyRefResponse> deleteVersionLobbyRef(@Valid DeleteVersionLobbyRefRequest request);

    Uni<GetVersionMatchmakerRequestResponse> getVersionMatchmakerRequest(
            @Valid GetVersionMatchmakerRequestRequest request);

    Uni<FindVersionMatchmakerRequestResponse> findVersionMatchmakerRequest(
            @Valid FindVersionMatchmakerRequestRequest request);

    Uni<ViewVersionMatchmakerRequestsResponse> viewVersionMatchmakerRequests(
            @Valid ViewVersionMatchmakerRequestsRequest request);

    Uni<SyncVersionMatchmakerRequestResponse> syncVersionMatchmakerRequest(
            @Valid SyncVersionMatchmakerRequestRequest request);

    Uni<DeleteVersionMatchmakerRequestResponse> deleteVersionMatchmakerRequest(
            @Valid DeleteVersionMatchmakerRequestRequest request);

    Uni<GetVersionMatchmakerRefResponse> getVersionMatchmakerRef(@Valid GetVersionMatchmakerRefRequest request);

    Uni<FindVersionMatchmakerRefResponse> findVersionMatchmakerRef(@Valid FindVersionMatchmakerRefRequest request);

    Uni<ViewVersionMatchmakerRefsResponse> viewVersionMatchmakerRefs(@Valid ViewVersionMatchmakerRefsRequest request);

    Uni<SyncVersionMatchmakerRefResponse> syncVersionMatchmakerRef(@Valid SyncVersionMatchmakerRefRequest request);

    Uni<DeleteVersionMatchmakerRefResponse> deleteVersionMatchmakerRef(
            @Valid DeleteVersionMatchmakerRefRequest request);

}
