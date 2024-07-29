package com.omgservers.service.module.tenant.impl.service.versionService;

import com.omgservers.schema.module.tenant.DeleteVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.DeleteVersionLobbyRefResponse;
import com.omgservers.schema.module.tenant.DeleteVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.DeleteVersionLobbyRequestResponse;
import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.DeleteVersionRequest;
import com.omgservers.schema.module.tenant.DeleteVersionResponse;
import com.omgservers.schema.module.tenant.FindVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.FindVersionLobbyRefResponse;
import com.omgservers.schema.module.tenant.FindVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.FindVersionLobbyRequestResponse;
import com.omgservers.schema.module.tenant.FindVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.FindVersionMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.FindVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.FindVersionMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.GetVersionConfigRequest;
import com.omgservers.schema.module.tenant.GetVersionConfigResponse;
import com.omgservers.schema.module.tenant.GetVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.GetVersionLobbyRefResponse;
import com.omgservers.schema.module.tenant.GetVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.GetVersionLobbyRequestResponse;
import com.omgservers.schema.module.tenant.GetVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.GetVersionMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.GetVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.GetVersionMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.GetVersionRequest;
import com.omgservers.schema.module.tenant.GetVersionResponse;
import com.omgservers.schema.module.tenant.SelectStageVersionRequest;
import com.omgservers.schema.module.tenant.SelectStageVersionResponse;
import com.omgservers.schema.module.tenant.SyncVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.SyncVersionLobbyRefResponse;
import com.omgservers.schema.module.tenant.SyncVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.SyncVersionLobbyRequestResponse;
import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.SyncVersionRequest;
import com.omgservers.schema.module.tenant.SyncVersionResponse;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRefsRequest;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRefsResponse;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRequestsRequest;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRequestsResponse;
import com.omgservers.schema.module.tenant.ViewVersionMatchmakerRefsRequest;
import com.omgservers.schema.module.tenant.ViewVersionMatchmakerRefsResponse;
import com.omgservers.schema.module.tenant.ViewVersionMatchmakerRequestsRequest;
import com.omgservers.schema.module.tenant.ViewVersionMatchmakerRequestsResponse;
import com.omgservers.schema.module.tenant.ViewVersionsRequest;
import com.omgservers.schema.module.tenant.ViewVersionsResponse;
import com.omgservers.schema.module.tenant.versionImageRef.DeleteVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.DeleteVersionImageRefResponse;
import com.omgservers.schema.module.tenant.versionImageRef.FindVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.FindVersionImageRefResponse;
import com.omgservers.schema.module.tenant.versionImageRef.GetVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.GetVersionImageRefResponse;
import com.omgservers.schema.module.tenant.versionImageRef.SyncVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.SyncVersionImageRefResponse;
import com.omgservers.schema.module.tenant.versionImageRef.ViewVersionImageRefsRequest;
import com.omgservers.schema.module.tenant.versionImageRef.ViewVersionImageRefsResponse;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.DeleteVersionJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.DeleteVersionJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.GetVersionJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.GetVersionJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.ViewVersionJenkinsRequestsRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.ViewVersionJenkinsRequestsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface VersionService {

    Uni<GetVersionResponse> getVersion(@Valid GetVersionRequest request);

    Uni<SyncVersionResponse> syncVersion(@Valid SyncVersionRequest request);

    Uni<ViewVersionsResponse> viewVersions(@Valid ViewVersionsRequest request);

    Uni<DeleteVersionResponse> deleteVersion(@Valid DeleteVersionRequest request);

    Uni<GetVersionConfigResponse> getVersionConfig(@Valid GetVersionConfigRequest request);

    Uni<GetVersionJenkinsRequestResponse> getVersionJenkinsRequest(@Valid GetVersionJenkinsRequestRequest request);

    Uni<ViewVersionJenkinsRequestsResponse> viewVersionJenkinsRequests(
            @Valid ViewVersionJenkinsRequestsRequest request);

    Uni<SyncVersionJenkinsRequestResponse> syncVersionJenkinsRequest(@Valid SyncVersionJenkinsRequestRequest request);

    Uni<SyncVersionJenkinsRequestResponse> syncVersionJenkinsRequestWithIdempotency(
            @Valid SyncVersionJenkinsRequestRequest request);

    Uni<DeleteVersionJenkinsRequestResponse> deleteVersionJenkinsRequest(
            @Valid DeleteVersionJenkinsRequestRequest request);

    Uni<GetVersionImageRefResponse> getVersionImageRef(@Valid GetVersionImageRefRequest request);

    Uni<FindVersionImageRefResponse> findVersionImageRef(@Valid FindVersionImageRefRequest request);

    Uni<ViewVersionImageRefsResponse> viewVersionImageRefs(@Valid ViewVersionImageRefsRequest request);

    Uni<SyncVersionImageRefResponse> syncVersionImageRef(@Valid SyncVersionImageRefRequest request);

    Uni<SyncVersionImageRefResponse> syncVersionImageRefWithIdempotency(@Valid SyncVersionImageRefRequest request);

    Uni<DeleteVersionImageRefResponse> deleteVersionImageRef(@Valid DeleteVersionImageRefRequest request);

    Uni<SelectStageVersionResponse> selectStageVersion(@Valid SelectStageVersionRequest request);

    Uni<GetVersionLobbyRequestResponse> getVersionLobbyRequest(@Valid GetVersionLobbyRequestRequest request);

    Uni<FindVersionLobbyRequestResponse> findVersionLobbyRequest(@Valid FindVersionLobbyRequestRequest request);

    Uni<ViewVersionLobbyRequestsResponse> viewVersionLobbyRequests(@Valid ViewVersionLobbyRequestsRequest request);

    Uni<SyncVersionLobbyRequestResponse> syncVersionLobbyRequest(@Valid SyncVersionLobbyRequestRequest request);

    Uni<SyncVersionLobbyRequestResponse> syncVersionLobbyRequestWithIdempotency(@Valid SyncVersionLobbyRequestRequest request);

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

    Uni<SyncVersionMatchmakerRequestResponse> syncVersionMatchmakerRequestWithIdempotency(
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
