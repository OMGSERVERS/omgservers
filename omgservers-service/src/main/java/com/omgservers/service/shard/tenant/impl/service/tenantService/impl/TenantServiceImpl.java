package com.omgservers.service.shard.tenant.impl.service.tenantService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenant.*;
import com.omgservers.schema.module.tenant.tenantBuildRequest.*;
import com.omgservers.schema.module.tenant.tenantDeployment.*;
import com.omgservers.schema.module.tenant.tenantFilesArchive.*;
import com.omgservers.schema.module.tenant.tenantImage.*;
import com.omgservers.schema.module.tenant.tenantLobbyRef.*;
import com.omgservers.schema.module.tenant.tenantLobbyResource.*;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.*;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.*;
import com.omgservers.schema.module.tenant.tenantPermission.*;
import com.omgservers.schema.module.tenant.tenantProject.*;
import com.omgservers.schema.module.tenant.tenantProjectPermission.*;
import com.omgservers.schema.module.tenant.tenantStage.*;
import com.omgservers.schema.module.tenant.tenantStagePermission.*;
import com.omgservers.schema.module.tenant.tenantVersion.*;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.CalculateShardOperation;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
import com.omgservers.service.shard.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.service.shard.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.service.shard.tenant.impl.service.tenantService.TenantService;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant.DeleteTenantMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant.GetTenantDataMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant.GetTenantMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant.SyncTenantMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantBuildRequest.DeleteTenantBuildRequestMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantBuildRequest.GetTenantBuildRequestMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantBuildRequest.SyncTenantBuildRequestMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantBuildRequest.ViewTenantBuildRequestsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeployment.*;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantFilesArchive.*;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage.*;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyRef.*;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyResource.*;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef.*;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantMatchmakerResource.*;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission.DeleteTenantPermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission.SyncTenantPermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission.VerifyTenantPermissionExistsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission.ViewTenantPermissionsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject.*;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission.DeleteTenantProjectPermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission.SyncTenantProjectPermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission.VerifyTenantProjectPermissionExistsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission.ViewTenantProjectPermissionsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage.*;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission.DeleteTenantStagePermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission.SyncTenantStagePermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission.VerifyTenantStagePermissionExistsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission.ViewTenantStagePermissionsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion.*;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantServiceImpl implements TenantService {

    final VerifyTenantProjectPermissionExistsMethod verifyTenantProjectPermissionExistsMethod;
    final VerifyTenantStagePermissionExistsMethod verifyTenantStagePermissionExistsMethod;
    final UpdateTenantLobbyResourceStatusMethod updateTenantLobbyResourceStatusMethod;
    final DeleteTenantMatchmakerResourceMethod deleteTenantMatchmakerResourceMethod;
    final DeleteTenantProjectPermissionMethod deleteTenantProjectPermissionMethod;
    final ViewTenantMatchmakerResourcesMethod viewTenantMatchmakerResourcesMethod;
    final ViewTenantProjectPermissionsMethod viewTenantProjectPermissionsMethod;
    final VerifyTenantPermissionExistsMethod verifyTenantPermissionExistsMethod;
    final FindTenantMatchmakerResourceMethod findTenantMatchmakerResourceMethod;
    final SyncTenantMatchmakerResourceMethod syncTenantMatchmakerResourceMethod;
    final DeleteTenantStagePermissionMethod deleteTenantStagePermissionMethod;
    final SyncTenantProjectPermissionMethod syncTenantProjectPermissionMethod;
    final GetTenantMatchmakerResourceMethod getTenantMatchmakerResourceMethod;
    final ViewTenantStagePermissionsMethod viewTenantStagePermissionsMethod;
    final DeleteTenantMatchmakerRefMethod deleteTenantMatchmakerRefMethod;
    final SyncTenantStagePermissionMethod syncTenantStagePermissionMethod;
    final DeleteTenantLobbyResourceMethod deleteTenantLobbyResourceMethod;
    final ViewTenantMatchmakerRefsMethod viewTenantMatchmakerRefsMethod;
    final DeleteTenantFilesArchiveMethod deleteTenantFilesArchiveMethod;
    final DeleteTenantBuildRequestMethod deleteTenantBuildRequestMethod;
    final ViewTenantLobbyResourcesMethod viewTenantLobbyResourcesMethod;
    final ViewTenantBuildRequestsMethod viewTenantBuildRequestsMethod;
    final SyncTenantMatchmakerRefMethod syncTenantMatchmakerRefMethod;
    final FindTenantMatchmakerRefMethod findTenantMatchmakerRefMethod;
    final GetTenantDeploymentDataMethod getTenantDeploymentDataMethod;
    final ViewTenantFilesArchivesMethod viewTenantFilesArchivesMethod;
    final FindTenantLobbyResourceMethod findTenantLobbyResourceMethod;
    final SyncTenantLobbyResourceMethod syncTenantLobbyResourceMethod;
    final FindTenantFilesArchiveMethod findTenantFilesArchiveMethod;
    final SyncTenantFilesArchiveMethod syncTenantFilesArchiveMethod;
    final GetTenantMatchmakerRefMethod getTenantMatchmakerRefMethod;
    final DeleteTenantPermissionMethod deleteTenantPermissionMethod;
    final GetTenantVersionConfigMethod getTenantVersionConfigMethod;
    final DeleteTenantDeploymentMethod deleteTenantDeploymentMethod;
    final SelectTenantDeploymentMethod selectTenantDeploymentMethod;
    final SyncTenantBuildRequestMethod syncTenantBuildRequestMethod;
    final GetTenantLobbyResourceMethod getTenantLobbyResourceMethod;
    final GetTenantBuildRequestMethod getTenantBuildRequestMethod;
    final ViewTenantPermissionsMethod viewTenantPermissionsMethod;
    final GetTenantFilesArchiveMethod getTenantFilesArchiveMethod;
    final GetTenantProjectDataMethod getTenantProjectDataMethod;
    final DeleteTenantLobbyRefMethod deleteTenantLobbyRefMethod;
    final SyncTenantPermissionMethod syncTenantPermissionMethod;
    final GetTenantVersionDataMethod getTenantVersionDataMethod;
    final SyncTenantDeploymentMethod syncTenantDeploymentMethod;
    final ViewTenantDeploymentMethod viewTenantDeploymentMethod;
    final ViewTenantLobbyRefsMethod viewTenantLobbyRefsMethod;
    final DeleteTenantVersionMethod deleteTenantVersionMethod;
    final DeleteTenantProjectMethod deleteTenantProjectMethod;
    final GetTenantDeploymentMethod getTenantDeploymentMethod;
    final SyncTenantLobbyRefMethod syncTenantLobbyRefMethod;
    final FindTenantLobbyRefMethod findTenantLobbyRefMethod;
    final GetTenantStageDataMethod getTenantStageDataMethod;
    final ViewTenantVersionsMethod viewTenantVersionsMethod;
    final ViewTenantProjectsMethod viewTenantProjectsMethod;
    final GetTenantLobbyRefMethod getTenantLobbyRefMethod;
    final SyncTenantVersionMethod syncTenantVersionMethod;
    final DeleteTenantStageMethod deleteTenantStageMethod;
    final SyncTenantProjectMethod syncTenantProjectMethod;
    final DeleteTenantImageMethod deleteTenantImageMethod;
    final GetTenantProjectMethod getTenantProjectMethod;
    final GetTenantVersionMethod getTenantVersionMethod;
    final ViewTenantStagesMethod viewTenantStagesMethod;
    final ViewTenantImagesMethod viewTenantImagesMethod;
    final SyncTenantStageMethod syncTenantStageMethod;
    final FindTenantImageMethod findTenantImageMethod;
    final SyncTenantImageMethod syncTenantImageMethod;
    final GetTenantImageMethod getTenantImageMethod;
    final GetTenantStageMethod getTenantStageMethod;
    final GetTenantDataMethod getTenantDataMethod;
    final DeleteTenantMethod deleteTenantMethod;
    final SyncTenantMethod syncTenantMethod;
    final GetTenantMethod getTenantMethod;

    final GetTenantModuleClientOperation getTenantModuleClientOperation;
    final HandleShardedRequestOperation handleShardedRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    /*
    Tenant
     */

    @Override
    public Uni<GetTenantResponse> getTenant(@Valid final GetTenantRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenant,
                getTenantMethod::getTenant);
    }

    @Override
    public Uni<GetTenantDataResponse> getTenantData(@Valid final GetTenantDataRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantData,
                getTenantDataMethod::getTenantData);
    }

    @Override
    public Uni<SyncTenantResponse> syncTenant(@Valid final SyncTenantRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenant,
                syncTenantMethod::syncTenant);
    }

    @Override
    public Uni<DeleteTenantResponse> deleteTenant(@Valid final DeleteTenantRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenant,
                deleteTenantMethod::deleteTenant);
    }

    /*
    TenantPermission
     */

    @Override
    public Uni<ViewTenantPermissionsResponse> viewTenantPermissions(@Valid final ViewTenantPermissionsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewTenantPermissions,
                viewTenantPermissionsMethod::execute);
    }

    @Override
    public Uni<VerifyTenantPermissionExistsResponse> verifyTenantPermissionExists(
            @Valid final VerifyTenantPermissionExistsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::verifyTenantPermissionExists,
                verifyTenantPermissionExistsMethod::execute);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(@Valid final SyncTenantPermissionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantPermission,
                syncTenantPermissionMethod::execute);
    }

    @Override
    public Uni<DeleteTenantPermissionResponse> deleteTenantPermission(
            @Valid final DeleteTenantPermissionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenantPermission,
                deleteTenantPermissionMethod::execute);
    }

    /*
    TenantProject
     */

    @Override
    public Uni<GetTenantProjectResponse> getTenantProject(@Valid final GetTenantProjectRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantProject,
                getTenantProjectMethod::execute);
    }

    @Override
    public Uni<GetTenantProjectDataResponse> getTenantProjectData(@Valid final GetTenantProjectDataRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantProjectData,
                getTenantProjectDataMethod::execute);
    }

    @Override
    public Uni<SyncTenantProjectResponse> syncTenantProject(@Valid final SyncTenantProjectRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantProject,
                syncTenantProjectMethod::execute);
    }

    @Override
    public Uni<ViewTenantProjectsResponse> viewTenantProjects(@Valid final ViewTenantProjectsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewTenantProjects,
                viewTenantProjectsMethod::execute);
    }

    @Override
    public Uni<DeleteTenantProjectResponse> deleteTenantProject(@Valid final DeleteTenantProjectRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenantProject,
                deleteTenantProjectMethod::execute);
    }

    /*
    TenantProjectPermission
     */

    @Override
    public Uni<ViewTenantProjectPermissionsResponse> viewTenantProjectPermissions(
            @Valid final ViewTenantProjectPermissionsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewTenantProjectPermissions,
                viewTenantProjectPermissionsMethod::execute);
    }

    @Override
    public Uni<VerifyTenantProjectPermissionExistsResponse> verifyTenantProjectPermissionExists(
            @Valid final VerifyTenantProjectPermissionExistsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::verifyTenantProjectPermissionExists,
                verifyTenantProjectPermissionExistsMethod::execute);
    }

    @Override
    public Uni<SyncTenantProjectPermissionResponse> syncTenantProjectPermission(
            @Valid final SyncTenantProjectPermissionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantProjectPermission,
                syncTenantProjectPermissionMethod::execute);
    }

    @Override
    public Uni<SyncTenantProjectPermissionResponse> syncTenantProjectPermissionWithIdempotency(
            SyncTenantProjectPermissionRequest request) {
        return syncTenantProjectPermission(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getTenantProjectPermission(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncTenantProjectPermissionResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteTenantProjectPermissionResponse> deleteTenantProjectPermission(
            @Valid final DeleteTenantProjectPermissionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenantProjectPermission,
                deleteTenantProjectPermissionMethod::execute);
    }

    /*
    TenantStage
     */

    @Override
    public Uni<GetTenantStageResponse> getTenantStage(@Valid final GetTenantStageRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantStage,
                getTenantStageMethod::execute);
    }

    @Override
    public Uni<GetTenantStageDataResponse> getTenantStageData(@Valid final GetTenantStageDataRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantStageData,
                getTenantStageDataMethod::execute);
    }

    @Override
    public Uni<SyncTenantStageResponse> syncTenantStage(@Valid final SyncTenantStageRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantStage,
                syncTenantStageMethod::execute);
    }

    @Override
    public Uni<ViewTenantStagesResponse> viewTenantStages(@Valid final ViewTenantStagesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewTenantStages,
                viewTenantStagesMethod::execute);
    }

    @Override
    public Uni<DeleteTenantStageResponse> deleteTenantStage(@Valid final DeleteTenantStageRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenantStage,
                deleteTenantStageMethod::execute);
    }

    /*
    TenantStagePermission
     */

    @Override
    public Uni<ViewTenantStagePermissionsResponse> viewTenantStagePermissions(
            @Valid final ViewTenantStagePermissionsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewTenantStagePermissions,
                viewTenantStagePermissionsMethod::execute);
    }

    @Override
    public Uni<VerifyTenantStagePermissionExistsResponse> verifyTenantStagePermissionExists(
            @Valid final VerifyTenantStagePermissionExistsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::verifyTenantStagePermissionExists,
                verifyTenantStagePermissionExistsMethod::execute);
    }

    @Override
    public Uni<SyncTenantStagePermissionResponse> syncTenantStagePermission(
            @Valid final SyncTenantStagePermissionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantStagePermission,
                syncTenantStagePermissionMethod::execute);
    }

    @Override
    public Uni<SyncTenantStagePermissionResponse> syncTenantStagePermissionWithIdempotency(
            @Valid final SyncTenantStagePermissionRequest request) {
        return syncTenantStagePermission(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getTenantStagePermission(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncTenantStagePermissionResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteTenantStagePermissionResponse> deleteTenantStagePermission(
            @Valid final DeleteTenantStagePermissionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenantStagePermission,
                deleteTenantStagePermissionMethod::execute);
    }

    /*
    TenantVersion
     */

    @Override
    public Uni<GetTenantVersionResponse> getTenantVersion(@Valid final GetTenantVersionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantVersion,
                getTenantVersionMethod::execute);
    }

    @Override
    public Uni<GetTenantVersionConfigResponse> getTenantVersionConfig(
            @Valid final GetTenantVersionConfigRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantVersionConfig,
                getTenantVersionConfigMethod::execute);
    }

    @Override
    public Uni<GetTenantVersionDataResponse> getTenantVersionData(GetTenantVersionDataRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantVersionData,
                getTenantVersionDataMethod::execute);
    }

    @Override
    public Uni<ViewTenantVersionsResponse> viewTenantVersions(@Valid final ViewTenantVersionsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewTenantVersions,
                viewTenantVersionsMethod::execute);
    }

    @Override
    public Uni<SyncTenantVersionResponse> syncTenantVersion(@Valid final SyncTenantVersionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantVersion,
                syncTenantVersionMethod::execute);
    }

    @Override
    public Uni<DeleteTenantVersionResponse> deleteTenantVersion(@Valid final DeleteTenantVersionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenantVersion,
                deleteTenantVersionMethod::execute);
    }

    /*
    TenantFilesArchive
     */

    @Override
    public Uni<GetTenantFilesArchiveResponse> getTenantFilesArchive(@Valid final GetTenantFilesArchiveRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantFilesArchive,
                getTenantFilesArchiveMethod::execute);
    }

    @Override
    public Uni<FindTenantFilesArchiveResponse> findTenantFilesArchive(
            @Valid final FindTenantFilesArchiveRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::findTenantFilesArchive,
                findTenantFilesArchiveMethod::execute);
    }

    @Override
    public Uni<ViewTenantFilesArchivesResponse> viewTenantFilesArchives(
            @Valid final ViewTenantFilesArchivesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewTenantFilesArchives,
                viewTenantFilesArchivesMethod::execute);
    }

    @Override
    public Uni<SyncTenantFilesArchiveResponse> syncTenantFilesArchive(
            @Valid final SyncTenantFilesArchiveRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantFilesArchive,
                syncTenantFilesArchiveMethod::execute);
    }

    @Override
    public Uni<DeleteTenantFilesArchiveResponse> deleteTenantFilesArchive(
            @Valid final DeleteTenantFilesArchiveRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenantFilesArchive,
                deleteTenantFilesArchiveMethod::execute);
    }

    /*
    TenantBuildRequest
     */

    @Override
    public Uni<GetTenantBuildRequestResponse> getTenantBuildRequest(
            @Valid final GetTenantBuildRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantBuildRequest,
                getTenantBuildRequestMethod::execute);
    }

    @Override
    public Uni<ViewTenantBuildRequestsResponse> viewTenantBuildRequests(
            @Valid final ViewTenantBuildRequestsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewTenantBuildRequests,
                viewTenantBuildRequestsMethod::execute);
    }

    @Override
    public Uni<SyncTenantBuildRequestResponse> syncTenantBuildRequest(
            @Valid final SyncTenantBuildRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantBuildRequest,
                syncTenantBuildRequestMethod::execute);
    }

    @Override
    public Uni<SyncTenantBuildRequestResponse> syncTenantBuildRequestWithIdempotency(
            @Valid final SyncTenantBuildRequestRequest request) {
        return syncTenantBuildRequest(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getTenantBuildRequest(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncTenantBuildRequestResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteTenantBuildRequestResponse> deleteTenantBuildRequest(
            @Valid final DeleteTenantBuildRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenantBuildRequest,
                deleteTenantBuildRequestMethod::execute);
    }

    /*
    TenantImage
     */

    @Override
    public Uni<GetTenantImageResponse> getTenantImage(@Valid final GetTenantImageRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantImage,
                getTenantImageMethod::execute);
    }

    @Override
    public Uni<FindTenantImageResponse> findTenantImage(@Valid final FindTenantImageRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::findTenantImage,
                findTenantImageMethod::execute);
    }

    @Override
    public Uni<ViewTenantImagesResponse> viewTenantImages(@Valid final ViewTenantImagesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewTenantImages,
                viewTenantImagesMethod::execute);
    }

    @Override
    public Uni<SyncTenantImageResponse> syncTenantImage(@Valid final SyncTenantImageRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantImage,
                syncTenantImageMethod::execute);
    }

    @Override
    public Uni<SyncTenantImageResponse> syncTenantImageWithIdempotency(
            @Valid final SyncTenantImageRequest request) {
        return syncTenantImage(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getTenantImage(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncTenantImageResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteTenantImageResponse> deleteTenantImage(@Valid final DeleteTenantImageRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenantImage,
                deleteTenantImageMethod::execute);
    }

    /*
    TenantDeployment
     */

    @Override
    public Uni<GetTenantDeploymentResponse> getTenantDeployment(@Valid final GetTenantDeploymentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantDeployment,
                getTenantDeploymentMethod::execute);
    }

    @Override
    public Uni<GetTenantDeploymentDataResponse> getTenantDeploymentData(GetTenantDeploymentDataRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantDeploymentData,
                getTenantDeploymentDataMethod::execute);
    }

    @Override
    public Uni<SelectTenantDeploymentResponse> selectTenantDeployment(
            @Valid final SelectTenantDeploymentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::selectTenantDeployment,
                selectTenantDeploymentMethod::execute);
    }

    @Override
    public Uni<ViewTenantDeploymentsResponse> viewTenantDeployments(@Valid final ViewTenantDeploymentsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewTenantDeployments,
                viewTenantDeploymentMethod::execute);
    }

    @Override
    public Uni<SyncTenantDeploymentResponse> syncTenantDeployment(@Valid final SyncTenantDeploymentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantDeployment,
                syncTenantDeploymentMethod::execute);
    }

    @Override
    public Uni<DeleteTenantDeploymentResponse> deleteTenantDeployment(
            @Valid final DeleteTenantDeploymentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenantDeployment,
                deleteTenantDeploymentMethod::execute);
    }

    /*
    TenantLobbyResource
     */

    @Override
    public Uni<GetTenantLobbyResourceResponse> execute(
            @Valid final GetTenantLobbyResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                getTenantLobbyResourceMethod::execute);
    }

    @Override
    public Uni<FindTenantLobbyResourceResponse> execute(
            @Valid final FindTenantLobbyResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                findTenantLobbyResourceMethod::execute);
    }

    @Override
    public Uni<ViewTenantLobbyResourcesResponse> execute(
            @Valid final ViewTenantLobbyResourcesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                viewTenantLobbyResourcesMethod::execute);
    }

    @Override
    public Uni<SyncTenantLobbyResourceResponse> execute(
            @Valid final SyncTenantLobbyResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                syncTenantLobbyResourceMethod::execute);
    }

    @Override
    public Uni<UpdateTenantLobbyResourceStatusResponse> execute(
            @Valid final UpdateTenantLobbyResourceStatusRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                updateTenantLobbyResourceStatusMethod::execute);
    }

    @Override
    public Uni<SyncTenantLobbyResourceResponse> executeWithIdempotency(
            @Valid final SyncTenantLobbyResourceRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getTenantLobbyResource(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncTenantLobbyResourceResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteTenantLobbyResourceResponse> execute(
            @Valid final DeleteTenantLobbyResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                deleteTenantLobbyResourceMethod::execute);
    }

    /*
    TenantLobbyRef
     */

    @Override
    public Uni<GetTenantLobbyRefResponse> getTenantLobbyRef(@Valid final GetTenantLobbyRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantLobbyRef,
                getTenantLobbyRefMethod::execute);
    }

    @Override
    public Uni<FindTenantLobbyRefResponse> findTenantLobbyRef(@Valid final FindTenantLobbyRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::findTenantLobbyRef,
                findTenantLobbyRefMethod::execute);
    }

    @Override
    public Uni<ViewTenantLobbyRefsResponse> viewTenantLobbyRefs(@Valid final ViewTenantLobbyRefsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewTenantLobbyRefs,
                viewTenantLobbyRefsMethod::execute);
    }

    @Override
    public Uni<SyncTenantLobbyRefResponse> syncTenantLobbyRef(@Valid final SyncTenantLobbyRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantLobbyRef,
                syncTenantLobbyRefMethod::execute);
    }

    @Override
    public Uni<SyncTenantLobbyRefResponse> syncTenantLobbyRefWithIdempotency(SyncTenantLobbyRefRequest request) {
        return syncTenantLobbyRef(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getTenantLobbyRef(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncTenantLobbyRefResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteTenantLobbyRefResponse> deleteTenantLobbyRef(@Valid final DeleteTenantLobbyRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenantLobbyRef,
                deleteTenantLobbyRefMethod::execute);
    }

    /*
    TenantMatchmakerResource
     */

    @Override
    public Uni<GetTenantMatchmakerResourceResponse> execute(@Valid final GetTenantMatchmakerResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                getTenantMatchmakerResourceMethod::execute);
    }

    @Override
    public Uni<FindTenantMatchmakerResourceResponse> execute(@Valid final FindTenantMatchmakerResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                findTenantMatchmakerResourceMethod::execute);
    }

    @Override
    public Uni<ViewTenantMatchmakerResourcesResponse> execute(@Valid final ViewTenantMatchmakerResourcesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                viewTenantMatchmakerResourcesMethod::execute);
    }

    @Override
    public Uni<SyncTenantMatchmakerResourceResponse> execute(@Valid final SyncTenantMatchmakerResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                syncTenantMatchmakerResourceMethod::execute);
    }

    @Override
    public Uni<SyncTenantMatchmakerResourceResponse> executeWithIdempotency(@Valid final SyncTenantMatchmakerResourceRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getTenantMatchmakerResource(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncTenantMatchmakerResourceResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteTenantMatchmakerResourceResponse> execute(@Valid final DeleteTenantMatchmakerResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                deleteTenantMatchmakerResourceMethod::execute);
    }

    /*
    TenantMatchmakerRef
     */

    @Override
    public Uni<GetTenantMatchmakerRefResponse> getTenantMatchmakerRef(
            @Valid final GetTenantMatchmakerRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantMatchmakerRef,
                getTenantMatchmakerRefMethod::execute);
    }

    @Override
    public Uni<FindTenantMatchmakerRefResponse> findTenantMatchmakerRef(
            @Valid final FindTenantMatchmakerRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::findTenantMatchmakerRef,
                findTenantMatchmakerRefMethod::execute);
    }

    @Override
    public Uni<ViewTenantMatchmakerRefsResponse> viewTenantMatchmakerRefs(
            @Valid final ViewTenantMatchmakerRefsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewTenantMatchmakerRefs,
                viewTenantMatchmakerRefsMethod::execute);
    }

    @Override
    public Uni<SyncTenantMatchmakerRefResponse> syncTenantMatchmakerRef(
            @Valid final SyncTenantMatchmakerRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantMatchmakerRef,
                syncTenantMatchmakerRefMethod::execute);
    }

    @Override
    public Uni<SyncTenantMatchmakerRefResponse> syncTenantMatchmakerRefWithIdempotency(
            SyncTenantMatchmakerRefRequest request) {
        return syncTenantMatchmakerRef(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getTenantMatchmakerRef(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncTenantMatchmakerRefResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteTenantMatchmakerRefResponse> deleteTenantMatchmakerRef(
            @Valid final DeleteTenantMatchmakerRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenantMatchmakerRef,
                deleteTenantMatchmakerRefMethod::execute);
    }


}
