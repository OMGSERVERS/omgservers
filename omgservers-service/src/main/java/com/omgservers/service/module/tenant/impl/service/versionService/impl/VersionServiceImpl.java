package com.omgservers.service.module.tenant.impl.service.versionService.impl;

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
import com.omgservers.service.module.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.service.module.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.service.module.tenant.impl.service.versionService.VersionService;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.deleteVersion.DeleteVersionMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.deleteVersionLobbyRef.DeleteVersionLobbyRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.deleteVersionLobbyRequest.DeleteVersionLobbyRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.deleteVersionMatchmakerRef.DeleteVersionMatchmakerRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.deleteVersionMatchmakerRequest.DeleteVersionMatchmakerRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.findStageVersionId.SelectStageVersionMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.findVersionLobbyRef.FindVersionLobbyRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.findVersionLobbyRequest.FindVersionLobbyRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.findVersionMatchmakerRef.FindVersionMatchmakerRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.findVersionMatchmakerRequest.FindVersionMatchmakerRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersion.GetVersionMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersionConfig.GetVersionConfigMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersionLobbyRef.GetVersionLobbyRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersionLobbyRequest.GetVersionLobbyRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersionMatchmakerRef.GetVersionMatchmakerRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersionMatchmakerRequest.GetVersionMatchmakerRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.syncVersion.SyncVersionMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.syncVersionLobbyRef.SyncVersionLobbyRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.syncVersionLobbyRequest.SyncVersionLobbyRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.syncVersionMatchmakerRef.SyncVersionMatchmakerRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.syncVersionMatchmakerRequest.SyncVersionMatchmakerRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.viewVersionLobbyRefs.ViewVersionLobbyRefsMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.viewVersionLobbyRequests.ViewVersionLobbyRequestsMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.viewVersionMatchmakerRefs.ViewVersionMatchmakerRefsMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.viewVersionMatchmakerRequests.ViewVersionMatchmakerRequestsMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.viewVersions.ViewVersionsMethod;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionServiceImpl implements VersionService {

    final DeleteVersionMatchmakerRequestMethod deleteVersionMatchmakerRequestMethod;
    final ViewVersionMatchmakerRequestsMethod viewVersionMatchmakerRequestsMethod;
    final FindVersionMatchmakerRequestMethod findVersionMatchmakerRequestMethod;
    final SyncVersionMatchmakerRequestMethod syncVersionMatchmakerRequestMethod;
    final GetVersionMatchmakerRequestMethod getVersionMatchmakerRequestMethod;
    final DeleteVersionMatchmakerRefMethod deleteVersionMatchmakerRefMethod;
    final ViewVersionMatchmakerRefsMethod viewVersionMatchmakerRefsMethod;
    final DeleteVersionLobbyRequestMethod deleteVersionLobbyRequestMethod;
    final SyncVersionMatchmakerRefMethod syncVersionMatchmakerRefMethod;
    final FindVersionMatchmakerRefMethod findVersionMatchmakerRefMethod;
    final ViewVersionLobbyRequestsMethod viewVersionLobbyRequestsMethod;
    final FindVersionLobbyRequestMethod findVersionLobbyRequestMethod;
    final SyncVersionLobbyRequestMethod syncVersionLobbyRequestMethod;
    final GetVersionLobbyRequestMethod getVersionLobbyRequestMethod;
    final DeleteVersionLobbyRefMethod deleteVersionLobbyRefMethod;
    final ViewVersionLobbyRefsMethod viewVersionLobbyRefsMethod;
    final SyncVersionLobbyRefMethod syncVersionLobbyRefMethod;
    final FindVersionLobbyRefMethod findVersionLobbyRefMethod;
    final GetVersionMatchmakerRefMethod getVersionMatchmaker;
    final SelectStageVersionMethod selectStageVersionMethod;
    final GetVersionLobbyRefMethod getVersionLobbyRefMethod;
    final GetVersionConfigMethod getVersionConfigMethod;
    final DeleteVersionMethod deleteVersionMethod;
    final ViewVersionsMethod viewVersionsMethod;
    final SyncVersionMethod syncVersionMethod;
    final GetVersionMethod getVersionMethod;

    final GetTenantModuleClientOperation getTenantModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetVersionResponse> getVersion(@Valid final GetVersionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersion,
                getVersionMethod::getVersion);
    }

    @Override
    public Uni<ViewVersionsResponse> viewVersions(@Valid final ViewVersionsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewVersions,
                viewVersionsMethod::viewVersions);
    }

    @Override
    public Uni<SelectStageVersionResponse> selectStageVersion(@Valid final SelectStageVersionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::selectStageVersion,
                selectStageVersionMethod::selectStageVersion);
    }

    @Override
    public Uni<SyncVersionResponse> syncVersion(@Valid final SyncVersionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncVersion,
                syncVersionMethod::syncVersion);
    }

    @Override
    public Uni<DeleteVersionResponse> deleteVersion(@Valid final DeleteVersionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteVersion,
                deleteVersionMethod::deleteVersion);
    }

    @Override
    public Uni<GetVersionConfigResponse> getVersionConfig(@Valid final GetVersionConfigRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersionConfig,
                getVersionConfigMethod::getVersionConfig);
    }

    @Override
    public Uni<GetVersionLobbyRequestResponse> getVersionLobbyRequest(
            @Valid final GetVersionLobbyRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersionLobbyRequest,
                getVersionLobbyRequestMethod::getVersionLobbyRequest);
    }

    @Override
    public Uni<FindVersionLobbyRequestResponse> findVersionLobbyRequest(
            @Valid final FindVersionLobbyRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::findVersionLobbyRequest,
                findVersionLobbyRequestMethod::findVersionLobbyRequest);
    }

    @Override
    public Uni<ViewVersionLobbyRequestsResponse> viewVersionLobbyRequests(
            @Valid final ViewVersionLobbyRequestsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewVersionLobbyRequests,
                viewVersionLobbyRequestsMethod::viewVersionLobbyRequests);
    }

    @Override
    public Uni<SyncVersionLobbyRequestResponse> syncVersionLobbyRequest(
            @Valid final SyncVersionLobbyRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncVersionLobbyRequest,
                syncVersionLobbyRequestMethod::syncVersionLobbyRequest);
    }

    @Override
    public Uni<DeleteVersionLobbyRequestResponse> deleteVersionLobbyRequest(
            @Valid final DeleteVersionLobbyRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteVersionLobbyRequest,
                deleteVersionLobbyRequestMethod::deleteVersionLobbyRequest);
    }

    @Override
    public Uni<GetVersionLobbyRefResponse> getVersionLobbyRef(@Valid final GetVersionLobbyRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersionLobbyRef,
                getVersionLobbyRefMethod::getVersionLobbyRef);
    }

    @Override
    public Uni<FindVersionLobbyRefResponse> findVersionLobbyRef(@Valid final FindVersionLobbyRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::findVersionLobbyRef,
                findVersionLobbyRefMethod::findVersionLobbyRef);
    }

    @Override
    public Uni<ViewVersionLobbyRefsResponse> viewVersionLobbyRefs(@Valid final ViewVersionLobbyRefsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewVersionLobbyRefs,
                viewVersionLobbyRefsMethod::viewVersionLobbyRefs);
    }

    @Override
    public Uni<SyncVersionLobbyRefResponse> syncVersionLobbyRef(@Valid final SyncVersionLobbyRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncVersionLobbyRef,
                syncVersionLobbyRefMethod::syncVersionLobbyRef);
    }

    @Override
    public Uni<DeleteVersionLobbyRefResponse> deleteVersionLobbyRef(@Valid final DeleteVersionLobbyRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteVersionLobbyRef,
                deleteVersionLobbyRefMethod::deleteVersionLobbyRef);
    }

    @Override
    public Uni<GetVersionMatchmakerRequestResponse> getVersionMatchmakerRequest(
            @Valid final GetVersionMatchmakerRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersionMatchmakerRequest,
                getVersionMatchmakerRequestMethod::getVersionMatchmakerRequest);
    }

    @Override
    public Uni<FindVersionMatchmakerRequestResponse> findVersionMatchmakerRequest(
            @Valid final FindVersionMatchmakerRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::findVersionMatchmakerRequest,
                findVersionMatchmakerRequestMethod::findVersionMatchmakerRequest);
    }

    @Override
    public Uni<ViewVersionMatchmakerRequestsResponse> viewVersionMatchmakerRequests(
            @Valid final ViewVersionMatchmakerRequestsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewVersionMatchmakerRequests,
                viewVersionMatchmakerRequestsMethod::viewVersionMatchmakerRequests);
    }

    @Override
    public Uni<SyncVersionMatchmakerRequestResponse> syncVersionMatchmakerRequest(
            @Valid final SyncVersionMatchmakerRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncVersionMatchmakerRequest,
                syncVersionMatchmakerRequestMethod::syncVersionMatchmakerRequest);
    }

    @Override
    public Uni<DeleteVersionMatchmakerRequestResponse> deleteVersionMatchmakerRequest(
            @Valid final DeleteVersionMatchmakerRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteVersionMatchmakerRequest,
                deleteVersionMatchmakerRequestMethod::deleteVersionMatchmakerRequest);
    }

    @Override
    public Uni<GetVersionMatchmakerRefResponse> getVersionMatchmakerRef(
            @Valid final GetVersionMatchmakerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersionMatchmakerRef,
                getVersionMatchmaker::getVersionMatchmakerRef);
    }

    @Override
    public Uni<FindVersionMatchmakerRefResponse> findVersionMatchmakerRef(
            @Valid final FindVersionMatchmakerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::findVersionMatchmakerRef,
                findVersionMatchmakerRefMethod::findVersionMatchmakerRef);
    }

    @Override
    public Uni<ViewVersionMatchmakerRefsResponse> viewVersionMatchmakerRefs(
            @Valid final ViewVersionMatchmakerRefsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewVersionMatchmakerRefs,
                viewVersionMatchmakerRefsMethod::viewVersionMatchmakerRefs);
    }

    @Override
    public Uni<SyncVersionMatchmakerRefResponse> syncVersionMatchmakerRef(
            @Valid final SyncVersionMatchmakerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncVersionMatchmakerRef,
                syncVersionMatchmakerRefMethod::syncVersionMatchmakerRef);
    }

    @Override
    public Uni<DeleteVersionMatchmakerRefResponse> deleteVersionMatchmakerRef(
            @Valid final DeleteVersionMatchmakerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteVersionMatchmakerRef,
                deleteVersionMatchmakerRefMethod::deleteVersionMatchmakerRef);
    }
}
