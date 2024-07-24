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
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.service.module.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.service.module.tenant.impl.service.versionService.VersionService;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.deleteVersion.DeleteVersionMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.findStageVersionId.SelectStageVersionMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.getVersion.GetVersionMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.getVersionConfig.GetVersionConfigMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.syncVersion.SyncVersionMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.viewVersions.ViewVersionsMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.deleteVersionImageRef.DeleteVersionImageRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.findVersionImageRef.FindVersionImageRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.getVersionImageRef.GetVersionImageRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.syncVersionImageRef.SyncVersionImageRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.viewVersionImageRefs.ViewVersionImageRefsMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionJenkinsRequest.deleteVersionJenkinsRequest.DeleteVersionJenkinsRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionJenkinsRequest.getVersionJenkinsRequest.GetVersionJenkinsRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionJenkinsRequest.syncVersionJenkinsRequest.SyncVersionJenkinsRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionJenkinsRequest.viewVersionJenkinsRequests.ViewVersionJenkinsRequestsMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.deleteVersionLobbyRef.DeleteVersionLobbyRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.findVersionLobbyRef.FindVersionLobbyRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.getVersionLobbyRef.GetVersionLobbyRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.syncVersionLobbyRef.SyncVersionLobbyRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.viewVersionLobbyRefs.ViewVersionLobbyRefsMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRequest.deleteVersionLobbyRequest.DeleteVersionLobbyRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRequest.findVersionLobbyRequest.FindVersionLobbyRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRequest.getVersionLobbyRequest.GetVersionLobbyRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRequest.syncVersionLobbyRequest.SyncVersionLobbyRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRequest.viewVersionLobbyRequests.ViewVersionLobbyRequestsMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRef.deleteVersionMatchmakerRef.DeleteVersionMatchmakerRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRef.findVersionMatchmakerRef.FindVersionMatchmakerRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRef.getVersionMatchmakerRef.GetVersionMatchmakerRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRef.syncVersionMatchmakerRef.SyncVersionMatchmakerRefMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRef.viewVersionMatchmakerRefs.ViewVersionMatchmakerRefsMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRequest.deleteVersionMatchmakerRequest.DeleteVersionMatchmakerRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRequest.findVersionMatchmakerRequest.FindVersionMatchmakerRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRequest.getVersionMatchmakerRequest.GetVersionMatchmakerRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRequest.syncVersionMatchmakerRequest.SyncVersionMatchmakerRequestMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRequest.viewVersionMatchmakerRequests.ViewVersionMatchmakerRequestsMethod;
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
    final DeleteVersionJenkinsRequestMethod deleteVersionJenkinsRequestMethod;
    final ViewVersionJenkinsRequestsMethod viewVersionJenkinsRequestsMethod;
    final DeleteVersionMatchmakerRefMethod deleteVersionMatchmakerRefMethod;
    final ViewVersionMatchmakerRefsMethod viewVersionMatchmakerRefsMethod;
    final DeleteVersionLobbyRequestMethod deleteVersionLobbyRequestMethod;
    final SyncVersionJenkinsRequestMethod syncVersionJenkinsRequestMethod;
    final GetVersionJenkinsRequestMethod getVersionJenkinsRequestMethod;
    final SyncVersionMatchmakerRefMethod syncVersionMatchmakerRefMethod;
    final FindVersionMatchmakerRefMethod findVersionMatchmakerRefMethod;
    final ViewVersionLobbyRequestsMethod viewVersionLobbyRequestsMethod;

    final FindVersionLobbyRequestMethod findVersionLobbyRequestMethod;
    final SyncVersionLobbyRequestMethod syncVersionLobbyRequestMethod;
    final GetVersionLobbyRequestMethod getVersionLobbyRequestMethod;
    final DeleteVersionLobbyRefMethod deleteVersionLobbyRefMethod;
    final DeleteVersionImageRefMethod deleteVersionImageRefMethod;
    final ViewVersionImageRefsMethod viewVersionImageRefsMethod;
    final ViewVersionLobbyRefsMethod viewVersionLobbyRefsMethod;
    final SyncVersionLobbyRefMethod syncVersionLobbyRefMethod;
    final FindVersionLobbyRefMethod findVersionLobbyRefMethod;
    final FindVersionImageRefMethod findVersionImageRefMethod;
    final SyncVersionImageRefMethod syncVersionImageRefMethod;
    final GetVersionMatchmakerRefMethod getVersionMatchmaker;
    final SelectStageVersionMethod selectStageVersionMethod;
    final GetVersionLobbyRefMethod getVersionLobbyRefMethod;
    final GetVersionImageRefMethod getVersionImageRefMethod;
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
    public Uni<GetVersionJenkinsRequestResponse> getVersionJenkinsRequest(
            @Valid final GetVersionJenkinsRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersionJenkinsRequest,
                getVersionJenkinsRequestMethod::getVersionJenkinsRequest);
    }

    @Override
    public Uni<ViewVersionJenkinsRequestsResponse> viewVersionJenkinsRequests(
            @Valid final ViewVersionJenkinsRequestsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewVersionJenkinsRequests,
                viewVersionJenkinsRequestsMethod::viewVersionJenkinsRequests);
    }

    @Override
    public Uni<SyncVersionJenkinsRequestResponse> syncVersionJenkinsRequest(
            @Valid final SyncVersionJenkinsRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncVersionJenkinsRequest,
                syncVersionJenkinsRequestMethod::syncVersionJenkinsRequest);
    }

    @Override
    public Uni<SyncVersionJenkinsRequestResponse> syncVersionJenkinsRequestWithIdempotency(
            @Valid final SyncVersionJenkinsRequestRequest request) {
        return syncVersionJenkinsRequest(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", request.getVersionJenkinsRequest(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncVersionJenkinsRequestResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteVersionJenkinsRequestResponse> deleteVersionJenkinsRequest(
            @Valid final DeleteVersionJenkinsRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteVersionJenkinsRequest,
                deleteVersionJenkinsRequestMethod::deleteVersionJenkinsRequest);
    }

    @Override
    public Uni<GetVersionImageRefResponse> getVersionImageRef(@Valid final GetVersionImageRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersionImageRef,
                getVersionImageRefMethod::getVersionImageRef);
    }

    @Override
    public Uni<FindVersionImageRefResponse> findVersionImageRef(@Valid final FindVersionImageRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::findVersionImageRef,
                findVersionImageRefMethod::findVersionImageRef);
    }

    @Override
    public Uni<ViewVersionImageRefsResponse> viewVersionImageRefs(@Valid final ViewVersionImageRefsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewVersionImageRefs,
                viewVersionImageRefsMethod::viewVersionImageRefs);
    }

    @Override
    public Uni<SyncVersionImageRefResponse> syncVersionImageRef(@Valid final SyncVersionImageRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncVersionImageRef,
                syncVersionImageRefMethod::syncVersionImageRef);
    }

    @Override
    public Uni<SyncVersionImageRefResponse> syncVersionImageRefWithIdempotency(
            @Valid final SyncVersionImageRefRequest request) {
        return syncVersionImageRef(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", request.getVersionImageRef(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncVersionImageRefResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteVersionImageRefResponse> deleteVersionImageRef(@Valid final DeleteVersionImageRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteVersionImageRef,
                deleteVersionImageRefMethod::deleteVersionImageRef);
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
                TenantModuleClient::viewVersionJenkinsRequests,
                viewVersionLobbyRequestsMethod::viewVersionLobbyRequests);
    }

    @Override
    public Uni<SyncVersionLobbyRequestResponse> syncVersionLobbyRequest(
            @Valid final SyncVersionLobbyRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncVersionJenkinsRequest,
                syncVersionLobbyRequestMethod::syncVersionLobbyRequest);
    }

    @Override
    public Uni<SyncVersionLobbyRequestResponse> syncVersionLobbyRequestWithIdempotency(
            @Valid final SyncVersionLobbyRequestRequest request) {
        return syncVersionLobbyRequest(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", request.getVersionLobbyRequest(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncVersionLobbyRequestResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteVersionLobbyRequestResponse> deleteVersionLobbyRequest(
            @Valid final DeleteVersionLobbyRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteVersionJenkinsRequest,
                deleteVersionLobbyRequestMethod::deleteVersionLobbyRequest);
    }

    @Override
    public Uni<GetVersionLobbyRefResponse> getVersionLobbyRef(@Valid final GetVersionLobbyRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersionImageRef,
                getVersionLobbyRefMethod::getVersionLobbyRef);
    }

    @Override
    public Uni<FindVersionLobbyRefResponse> findVersionLobbyRef(@Valid final FindVersionLobbyRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::findVersionImageRef,
                findVersionLobbyRefMethod::findVersionLobbyRef);
    }

    @Override
    public Uni<ViewVersionLobbyRefsResponse> viewVersionLobbyRefs(@Valid final ViewVersionLobbyRefsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewVersionImageRefs,
                viewVersionLobbyRefsMethod::viewVersionLobbyRefs);
    }

    @Override
    public Uni<SyncVersionLobbyRefResponse> syncVersionLobbyRef(@Valid final SyncVersionLobbyRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncVersionImageRef,
                syncVersionLobbyRefMethod::syncVersionLobbyRef);
    }

    @Override
    public Uni<DeleteVersionLobbyRefResponse> deleteVersionLobbyRef(@Valid final DeleteVersionLobbyRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteVersionImageRef,
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
    public Uni<SyncVersionMatchmakerRequestResponse> syncVersionMatchmakerRequestWithIdempotency(
            @Valid final SyncVersionMatchmakerRequestRequest request) {
        return syncVersionMatchmakerRequest(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", request.getVersionMatchmakerRequest(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncVersionMatchmakerRequestResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
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
