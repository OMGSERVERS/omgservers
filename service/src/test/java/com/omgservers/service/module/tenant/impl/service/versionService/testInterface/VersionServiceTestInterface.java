package com.omgservers.service.module.tenant.impl.service.versionService.testInterface;

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
import com.omgservers.model.dto.tenant.versionImageRef.DeleteVersionImageRefRequest;
import com.omgservers.model.dto.tenant.versionImageRef.DeleteVersionImageRefResponse;
import com.omgservers.model.dto.tenant.versionImageRef.FindVersionImageRefRequest;
import com.omgservers.model.dto.tenant.versionImageRef.FindVersionImageRefResponse;
import com.omgservers.model.dto.tenant.versionImageRef.GetVersionImageRefRequest;
import com.omgservers.model.dto.tenant.versionImageRef.GetVersionImageRefResponse;
import com.omgservers.model.dto.tenant.versionImageRef.SyncVersionImageRefRequest;
import com.omgservers.model.dto.tenant.versionImageRef.SyncVersionImageRefResponse;
import com.omgservers.model.dto.tenant.versionImageRef.ViewVersionImageRefsRequest;
import com.omgservers.model.dto.tenant.versionImageRef.ViewVersionImageRefsResponse;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.DeleteVersionJenkinsRequestRequest;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.DeleteVersionJenkinsRequestResponse;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.GetVersionJenkinsRequestRequest;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.GetVersionJenkinsRequestResponse;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestRequest;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestResponse;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.ViewVersionJenkinsRequestsRequest;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.ViewVersionJenkinsRequestsResponse;
import com.omgservers.service.module.tenant.impl.service.versionService.VersionService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final VersionService versionService;

    public GetVersionResponse getVersion(final GetVersionRequest request) {
        return versionService.getVersion(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncVersionResponse syncVersion(final SyncVersionRequest request) {
        return versionService.syncVersion(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewVersionsResponse viewVersions(final ViewVersionsRequest request) {
        return versionService.viewVersions(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteVersionResponse deleteVersion(final DeleteVersionRequest request) {
        return versionService.deleteVersion(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetVersionConfigResponse getVersionConfig(final GetVersionConfigRequest request) {
        return versionService.getVersionConfig(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetVersionJenkinsRequestResponse getVersionJenkinsRequest(final GetVersionJenkinsRequestRequest request) {
        return versionService.getVersionJenkinsRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewVersionJenkinsRequestsResponse viewVersionJenkinsRequests(
            final ViewVersionJenkinsRequestsRequest request) {
        return versionService.viewVersionJenkinsRequests(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncVersionJenkinsRequestResponse syncVersionJenkinsRequest(final SyncVersionJenkinsRequestRequest request) {
        return versionService.syncVersionJenkinsRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncVersionJenkinsRequestResponse syncVersionJenkinsRequestWithIdempotency(
            final SyncVersionJenkinsRequestRequest request) {
        return versionService.syncVersionJenkinsRequestWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteVersionJenkinsRequestResponse deleteVersionJenkinsRequest(
            final DeleteVersionJenkinsRequestRequest request) {
        return versionService.deleteVersionJenkinsRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetVersionImageRefResponse getVersionImageRef(final GetVersionImageRefRequest request) {
        return versionService.getVersionImageRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindVersionImageRefResponse findVersionImageRef(final FindVersionImageRefRequest request) {
        return versionService.findVersionImageRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewVersionImageRefsResponse viewVersionImageRefs(final ViewVersionImageRefsRequest request) {
        return versionService.viewVersionImageRefs(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncVersionImageRefResponse syncVersionImageRef(final SyncVersionImageRefRequest request) {
        return versionService.syncVersionImageRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncVersionImageRefResponse syncVersionImageRefWithIdempotency(final SyncVersionImageRefRequest request) {
        return versionService.syncVersionImageRefWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteVersionImageRefResponse deleteVersionImageRef(final DeleteVersionImageRefRequest request) {
        return versionService.deleteVersionImageRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SelectStageVersionResponse selectStageVersion(
            final SelectStageVersionRequest request) {
        return versionService.selectStageVersion(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetVersionLobbyRequestResponse getVersionLobbyRequest(
            final GetVersionLobbyRequestRequest request) {
        return versionService.getVersionLobbyRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindVersionLobbyRequestResponse findVersionLobbyRequest(
            final FindVersionLobbyRequestRequest request) {
        return versionService.findVersionLobbyRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewVersionLobbyRequestsResponse viewVersionLobbyRequests(
            final ViewVersionLobbyRequestsRequest request) {
        return versionService.viewVersionLobbyRequests(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncVersionLobbyRequestResponse syncVersionLobbyRequest(
            final SyncVersionLobbyRequestRequest request) {
        return versionService.syncVersionLobbyRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteVersionLobbyRequestResponse deleteVersionLobbyRequest(
            final DeleteVersionLobbyRequestRequest request) {
        return versionService.deleteVersionLobbyRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetVersionLobbyRefResponse getVersionLobbyRef(
            final GetVersionLobbyRefRequest request) {
        return versionService.getVersionLobbyRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindVersionLobbyRefResponse findVersionLobbyRef(
            final FindVersionLobbyRefRequest request) {
        return versionService.findVersionLobbyRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewVersionLobbyRefsResponse viewVersionLobbyRefs(
            final ViewVersionLobbyRefsRequest request) {
        return versionService.viewVersionLobbyRefs(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncVersionLobbyRefResponse syncVersionLobbyRef(
            final SyncVersionLobbyRefRequest request) {
        return versionService.syncVersionLobbyRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteVersionLobbyRefResponse deleteVersionLobbyRef(
            final DeleteVersionLobbyRefRequest request) {
        return versionService.deleteVersionLobbyRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetVersionMatchmakerRequestResponse getVersionMatchmakerRequest(
            final GetVersionMatchmakerRequestRequest request) {
        return versionService.getVersionMatchmakerRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindVersionMatchmakerRequestResponse findVersionMatchmakerRequest(
            final FindVersionMatchmakerRequestRequest request) {
        return versionService.findVersionMatchmakerRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewVersionMatchmakerRequestsResponse viewVersionMatchmakerRequests(
            final ViewVersionMatchmakerRequestsRequest request) {
        return versionService.viewVersionMatchmakerRequests(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncVersionMatchmakerRequestResponse syncVersionMatchmakerRequest(
            final SyncVersionMatchmakerRequestRequest request) {
        return versionService.syncVersionMatchmakerRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteVersionMatchmakerRequestResponse deleteVersionMatchmakerRequest(
            final DeleteVersionMatchmakerRequestRequest request) {
        return versionService.deleteVersionMatchmakerRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetVersionMatchmakerRefResponse getVersionMatchmakerRef(
            final GetVersionMatchmakerRefRequest request) {
        return versionService.getVersionMatchmakerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindVersionMatchmakerRefResponse findVersionMatchmakerRef(
            final FindVersionMatchmakerRefRequest request) {
        return versionService.findVersionMatchmakerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewVersionMatchmakerRefsResponse viewVersionMatchmakerRefs(
            final ViewVersionMatchmakerRefsRequest request) {
        return versionService.viewVersionMatchmakerRefs(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncVersionMatchmakerRefResponse syncVersionMatchmakerRef(
            final SyncVersionMatchmakerRefRequest request) {
        return versionService.syncVersionMatchmakerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteVersionMatchmakerRefResponse deleteVersionMatchmakerRef(
            final DeleteVersionMatchmakerRefRequest request) {
        return versionService.deleteVersionMatchmakerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
