package com.omgservers.service.module.tenant.impl.service.versionService.testInterface;

import com.omgservers.schema.module.tenant.tenantLobbyRef.DeleteTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.DeleteTenantLobbyRefResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.DeleteTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.DeleteTenantMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.FindTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.FindTenantLobbyRefResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.FindTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.FindTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.FindTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.FindTenantMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.FindTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.FindTenantMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionConfigRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionConfigResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.GetTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.GetTenantLobbyRefResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.GetTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.GetTenantMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.GetTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.GetTenantMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.SelectTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.SelectTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.SyncTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.SyncTenantLobbyRefResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsResponse;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsRequest;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsResponse;
import com.omgservers.schema.module.tenant.tenantImageRef.DeleteTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.DeleteTenantImageRefResponse;
import com.omgservers.schema.module.tenant.tenantImageRef.FindTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.FindTenantImageRefResponse;
import com.omgservers.schema.module.tenant.tenantImageRef.GetTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.GetTenantImageRefResponse;
import com.omgservers.schema.module.tenant.tenantImageRef.SyncTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.SyncTenantImageRefResponse;
import com.omgservers.schema.module.tenant.tenantImageRef.ViewTenantImageRefsRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.ViewTenantImageRefsResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.DeleteTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.DeleteTenantJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.GetTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.GetTenantJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.SyncTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.SyncTenantJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.ViewTenantJenkinsRequestsRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.ViewTenantJenkinsRequestsResponse;
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

    public GetTenantVersionResponse getVersion(final GetTenantVersionRequest request) {
        return versionService.getVersion(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantVersionResponse syncVersion(final SyncTenantVersionRequest request) {
        return versionService.syncVersion(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantVersionsResponse viewVersions(final ViewTenantVersionsRequest request) {
        return versionService.viewVersions(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantVersionResponse deleteVersion(final DeleteTenantVersionRequest request) {
        return versionService.deleteVersion(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantVersionConfigResponse getVersionConfig(final GetTenantVersionConfigRequest request) {
        return versionService.getVersionConfig(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantJenkinsRequestResponse getVersionJenkinsRequest(final GetTenantJenkinsRequestRequest request) {
        return versionService.getVersionJenkinsRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantJenkinsRequestsResponse viewVersionJenkinsRequests(
            final ViewTenantJenkinsRequestsRequest request) {
        return versionService.viewVersionJenkinsRequests(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantJenkinsRequestResponse syncVersionJenkinsRequest(final SyncTenantJenkinsRequestRequest request) {
        return versionService.syncVersionJenkinsRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantJenkinsRequestResponse syncVersionJenkinsRequestWithIdempotency(
            final SyncTenantJenkinsRequestRequest request) {
        return versionService.syncVersionJenkinsRequestWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantJenkinsRequestResponse deleteVersionJenkinsRequest(
            final DeleteTenantJenkinsRequestRequest request) {
        return versionService.deleteVersionJenkinsRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantImageRefResponse getVersionImageRef(final GetTenantImageRefRequest request) {
        return versionService.getVersionImageRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindTenantImageRefResponse findVersionImageRef(final FindTenantImageRefRequest request) {
        return versionService.findVersionImageRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantImageRefsResponse viewVersionImageRefs(final ViewTenantImageRefsRequest request) {
        return versionService.viewVersionImageRefs(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantImageRefResponse syncVersionImageRef(final SyncTenantImageRefRequest request) {
        return versionService.syncVersionImageRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantImageRefResponse syncVersionImageRefWithIdempotency(final SyncTenantImageRefRequest request) {
        return versionService.syncVersionImageRefWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantImageRefResponse deleteVersionImageRef(final DeleteTenantImageRefRequest request) {
        return versionService.deleteVersionImageRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SelectTenantDeploymentResponse selectStageVersion(
            final SelectTenantDeploymentRequest request) {
        return versionService.selectStageVersion(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantLobbyRequestResponse getVersionLobbyRequest(
            final GetTenantLobbyRequestRequest request) {
        return versionService.getVersionLobbyRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindTenantLobbyRequestResponse findVersionLobbyRequest(
            final FindTenantLobbyRequestRequest request) {
        return versionService.findVersionLobbyRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantLobbyRequestsResponse viewVersionLobbyRequests(
            final ViewTenantLobbyRequestsRequest request) {
        return versionService.viewVersionLobbyRequests(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantLobbyRequestResponse syncVersionLobbyRequest(
            final SyncTenantLobbyRequestRequest request) {
        return versionService.syncVersionLobbyRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantLobbyRequestResponse deleteVersionLobbyRequest(
            final DeleteTenantLobbyRequestRequest request) {
        return versionService.deleteVersionLobbyRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantLobbyRefResponse getVersionLobbyRef(
            final GetTenantLobbyRefRequest request) {
        return versionService.getVersionLobbyRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindTenantLobbyRefResponse findVersionLobbyRef(
            final FindTenantLobbyRefRequest request) {
        return versionService.findVersionLobbyRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantLobbyRefsResponse viewVersionLobbyRefs(
            final ViewTenantLobbyRefsRequest request) {
        return versionService.viewVersionLobbyRefs(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantLobbyRefResponse syncVersionLobbyRef(
            final SyncTenantLobbyRefRequest request) {
        return versionService.syncVersionLobbyRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantLobbyRefResponse deleteVersionLobbyRef(
            final DeleteTenantLobbyRefRequest request) {
        return versionService.deleteVersionLobbyRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantMatchmakerRequestResponse getVersionMatchmakerRequest(
            final GetTenantMatchmakerRequestRequest request) {
        return versionService.getVersionMatchmakerRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindTenantMatchmakerRequestResponse findVersionMatchmakerRequest(
            final FindTenantMatchmakerRequestRequest request) {
        return versionService.findVersionMatchmakerRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantMatchmakerRequestsResponse viewVersionMatchmakerRequests(
            final ViewTenantMatchmakerRequestsRequest request) {
        return versionService.viewVersionMatchmakerRequests(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantMatchmakerRequestResponse syncVersionMatchmakerRequest(
            final SyncTenantMatchmakerRequestRequest request) {
        return versionService.syncVersionMatchmakerRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantMatchmakerRequestResponse deleteVersionMatchmakerRequest(
            final DeleteTenantMatchmakerRequestRequest request) {
        return versionService.deleteVersionMatchmakerRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantMatchmakerRefResponse getVersionMatchmakerRef(
            final GetTenantMatchmakerRefRequest request) {
        return versionService.getVersionMatchmakerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindTenantMatchmakerRefResponse findVersionMatchmakerRef(
            final FindTenantMatchmakerRefRequest request) {
        return versionService.findVersionMatchmakerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantMatchmakerRefsResponse viewVersionMatchmakerRefs(
            final ViewTenantMatchmakerRefsRequest request) {
        return versionService.viewVersionMatchmakerRefs(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantMatchmakerRefResponse syncVersionMatchmakerRef(
            final SyncTenantMatchmakerRefRequest request) {
        return versionService.syncVersionMatchmakerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantMatchmakerRefResponse deleteVersionMatchmakerRef(
            final DeleteTenantMatchmakerRefRequest request) {
        return versionService.deleteVersionMatchmakerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
