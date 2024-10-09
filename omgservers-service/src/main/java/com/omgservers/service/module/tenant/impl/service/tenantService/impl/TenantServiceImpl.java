package com.omgservers.service.module.tenant.impl.service.tenantService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenant.DeleteTenantRequest;
import com.omgservers.schema.module.tenant.tenant.DeleteTenantResponse;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataResponse;
import com.omgservers.schema.module.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantResponse;
import com.omgservers.schema.module.tenant.tenant.SyncTenantRequest;
import com.omgservers.schema.module.tenant.tenant.SyncTenantResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.DeleteTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.DeleteTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentDataRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentDataResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.SelectTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.SelectTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.SyncTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.SyncTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.ViewTenantDeploymentsRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.ViewTenantDeploymentsResponse;
import com.omgservers.schema.module.tenant.tenantFilesArchive.DeleteTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.DeleteTenantFilesArchiveResponse;
import com.omgservers.schema.module.tenant.tenantFilesArchive.FindTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.FindTenantFilesArchiveResponse;
import com.omgservers.schema.module.tenant.tenantFilesArchive.GetTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.GetTenantFilesArchiveResponse;
import com.omgservers.schema.module.tenant.tenantFilesArchive.SyncTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.SyncTenantFilesArchiveResponse;
import com.omgservers.schema.module.tenant.tenantFilesArchive.ViewTenantFilesArchivesRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.ViewTenantFilesArchivesResponse;
import com.omgservers.schema.module.tenant.tenantImage.DeleteTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.DeleteTenantImageResponse;
import com.omgservers.schema.module.tenant.tenantImage.FindTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.FindTenantImageResponse;
import com.omgservers.schema.module.tenant.tenantImage.GetTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.GetTenantImageResponse;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageResponse;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesRequest;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.DeleteTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.DeleteTenantJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.GetTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.GetTenantJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.SyncTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.SyncTenantJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.ViewTenantJenkinsRequestsRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.ViewTenantJenkinsRequestsResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.DeleteTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.DeleteTenantLobbyRefResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.FindTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.FindTenantLobbyRefResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.GetTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.GetTenantLobbyRefResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.SyncTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.SyncTenantLobbyRefResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.FindTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.FindTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.DeleteTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.DeleteTenantMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.FindTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.FindTenantMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.GetTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.GetTenantMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.FindTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.FindTenantMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.GetTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.GetTenantMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsResponse;
import com.omgservers.schema.module.tenant.tenantPermission.DeleteTenantPermissionRequest;
import com.omgservers.schema.module.tenant.tenantPermission.DeleteTenantPermissionResponse;
import com.omgservers.schema.module.tenant.tenantPermission.SyncTenantPermissionRequest;
import com.omgservers.schema.module.tenant.tenantPermission.SyncTenantPermissionResponse;
import com.omgservers.schema.module.tenant.tenantPermission.VerifyTenantPermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantPermission.VerifyTenantPermissionExistsResponse;
import com.omgservers.schema.module.tenant.tenantPermission.ViewTenantPermissionsRequest;
import com.omgservers.schema.module.tenant.tenantPermission.ViewTenantPermissionsResponse;
import com.omgservers.schema.module.tenant.tenantProject.DeleteTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.DeleteTenantProjectResponse;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectDataRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectDataResponse;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectResponse;
import com.omgservers.schema.module.tenant.tenantProject.ViewTenantProjectsRequest;
import com.omgservers.schema.module.tenant.tenantProject.ViewTenantProjectsResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.DeleteTenantProjectPermissionRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.DeleteTenantProjectPermissionResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.SyncTenantProjectPermissionRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.SyncTenantProjectPermissionResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsResponse;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStage.ViewTenantStagesRequest;
import com.omgservers.schema.module.tenant.tenantStage.ViewTenantStagesResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsResponse;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionConfigRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionConfigResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionDataRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionDataResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsRequest;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.service.module.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.service.module.tenant.impl.service.tenantService.TenantService;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant.DeleteTenantMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant.GetTenantDataMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant.GetTenantMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant.SyncTenantMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantDeployment.DeleteTenantDeploymentMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantDeployment.GetTenantDeploymentDataMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantDeployment.GetTenantDeploymentMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantDeployment.SelectTenantDeploymentMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantDeployment.SyncTenantDeploymentMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantDeployment.ViewTenantDeploymentMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantFilesArchive.DeleteTenantFilesArchiveMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantFilesArchive.FindTenantFilesArchiveMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantFilesArchive.GetTenantFilesArchiveMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantFilesArchive.SyncTenantFilesArchiveMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantFilesArchive.ViewTenantFilesArchivesMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImage.DeleteTenantImageMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImage.FindTenantImageMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImage.GetTenantImageMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImage.SyncTenantImageMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImage.ViewTenantImagesMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantJenkinsRequest.DeleteTenantJenkinsRequestMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantJenkinsRequest.GetTenantJenkinsRequestMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantJenkinsRequest.SyncTenantJenkinsRequestMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantJenkinsRequest.ViewTenantJenkinsRequestsMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRef.DeleteTenantLobbyRefMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRef.FindTenantLobbyRefMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRef.GetTenantLobbyRefMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRef.SyncTenantLobbyRefMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRef.ViewTenantLobbyRefsMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRequest.DeleteTenantLobbyRequestMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRequest.FindTenantLobbyRequestMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRequest.GetTenantLobbyRequestMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRequest.SyncTenantLobbyRequestMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRequest.ViewTenantLobbyRequestsMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef.DeleteTenantMatchmakerRefMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef.FindTenantMatchmakerRefMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef.GetTenantMatchmakerRefMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef.SyncTenantMatchmakerRefMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef.ViewTenantMatchmakerRefsMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRequest.FindTenantMatchmakerRequestMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRequest.GetTenantMatchmakerRequestMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRequest.SyncTenantMatchmakerRequestMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission.DeleteTenantPermissionMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission.SyncTenantPermissionMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission.VerifyTenantPermissionExistsMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission.ViewTenantPermissionsMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProject.DeleteTenantProjectMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProject.GetTenantProjectDataMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProject.GetTenantProjectMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProject.SyncTenantProjectMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProject.ViewTenantProjectsMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProjectPermission.DeleteTenantProjectPermissionMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProjectPermission.SyncTenantProjectPermissionMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProjectPermission.VerifyTenantProjectPermissionExistsMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProjectPermission.ViewTenantProjectPermissionsMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStage.DeleteTenantStageMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStage.GetTenantStageDataMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStage.GetTenantStageMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStage.SyncTenantStageMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStage.ViewTenantStagesMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStagePermission.DeleteTenantStagePermissionMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStagePermission.SyncTenantStagePermissionMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStagePermission.VerifyTenantStagePermissionExistsMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStagePermission.ViewTenantStagePermissionsMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantVersion.DeleteTenantVersionMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantVersion.GetTenantVersionConfigMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantVersion.GetTenantVersionDataMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantVersion.GetTenantVersionMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantVersion.SyncTenantVersionMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantVersion.ViewTenantVersionsMethod;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.operation.handleInternalRequest.HandleShardedRequestOperation;
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
    final DeleteTenantProjectPermissionMethod deleteTenantProjectPermissionMethod;
    final ViewTenantMatchmakerRequestsMethod viewTenantMatchmakerRequestsMethod;
    final ViewTenantProjectPermissionsMethod viewTenantProjectPermissionsMethod;
    final VerifyTenantPermissionExistsMethod verifyTenantPermissionExistsMethod;
    final DeleteTenantMatchmakerRequestMethod deleteTenantMatchmakerRequestMethod;
    final FindTenantMatchmakerRequestMethod findTenantMatchmakerRequestMethod;
    final SyncTenantMatchmakerRequestMethod syncTenantMatchmakerRequestMethod;
    final DeleteTenantStagePermissionMethod deleteTenantStagePermissionMethod;
    final SyncTenantProjectPermissionMethod syncTenantProjectPermissionMethod;
    final GetTenantMatchmakerRequestMethod getTenantMatchmakerRequestMethod;
    final DeleteTenantJenkinsRequestMethod deleteTenantJenkinsRequestMethod;
    final ViewTenantStagePermissionsMethod viewTenantStagePermissionsMethod;
    final ViewTenantJenkinsRequestsMethod viewTenantJenkinsRequestsMethod;
    final DeleteTenantMatchmakerRefMethod deleteTenantMatchmakerRefMethod;
    final SyncTenantStagePermissionMethod syncTenantStagePermissionMethod;
    final SyncTenantJenkinsRequestMethod syncTenantJenkinsRequestMethod;
    final ViewTenantMatchmakerRefsMethod viewTenantMatchmakerRefsMethod;
    final DeleteTenantLobbyRequestMethod deleteTenantLobbyRequestMethod;
    final DeleteTenantFilesArchiveMethod deleteTenantFilesArchiveMethod;
    final GetTenantJenkinsRequestMethod getTenantJenkinsRequestMethod;
    final SyncTenantMatchmakerRefMethod syncTenantMatchmakerRefMethod;
    final FindTenantMatchmakerRefMethod findTenantMatchmakerRefMethod;
    final ViewTenantLobbyRequestsMethod viewTenantLobbyRequestsMethod;
    final GetTenantDeploymentDataMethod getTenantDeploymentDataMethod;
    final ViewTenantFilesArchivesMethod viewTenantFilesArchivesMethod;
    final FindTenantFilesArchiveMethod findTenantFilesArchiveMethod;
    final SyncTenantFilesArchiveMethod syncTenantFilesArchiveMethod;
    final FindTenantLobbyRequestMethod findTenantLobbyRequestMethod;
    final SyncTenantLobbyRequestMethod syncTenantLobbyRequestMethod;
    final GetTenantMatchmakerRefMethod getTenantMatchmakerRefMethod;
    final DeleteTenantPermissionMethod deleteTenantPermissionMethod;
    final GetTenantVersionConfigMethod getTenantVersionConfigMethod;
    final DeleteTenantDeploymentMethod deleteTenantDeploymentMethod;
    final SelectTenantDeploymentMethod selectTenantDeploymentMethod;
    final GetTenantLobbyRequestMethod getTenantLobbyRequestMethod;
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
                            log.warn("Idempotency was violated, object={}, {}", request.getTenantProjectPermission(),
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
                            log.warn("Idempotency was violated, object={}, {}", request.getTenantStagePermission(),
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
    TenantJenkinsRequest
     */

    @Override
    public Uni<GetTenantJenkinsRequestResponse> getTenantJenkinsRequest(
            @Valid final GetTenantJenkinsRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantJenkinsRequest,
                getTenantJenkinsRequestMethod::execute);
    }

    @Override
    public Uni<ViewTenantJenkinsRequestsResponse> viewTenantJenkinsRequests(
            @Valid final ViewTenantJenkinsRequestsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewTenantJenkinsRequests,
                viewTenantJenkinsRequestsMethod::execute);
    }

    @Override
    public Uni<SyncTenantJenkinsRequestResponse> syncTenantJenkinsRequest(
            @Valid final SyncTenantJenkinsRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantJenkinsRequest,
                syncTenantJenkinsRequestMethod::execute);
    }

    @Override
    public Uni<SyncTenantJenkinsRequestResponse> syncTenantJenkinsRequestWithIdempotency(
            @Valid final SyncTenantJenkinsRequestRequest request) {
        return syncTenantJenkinsRequest(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", request.getTenantJenkinsRequest(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncTenantJenkinsRequestResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteTenantJenkinsRequestResponse> deleteTenantJenkinsRequest(
            @Valid final DeleteTenantJenkinsRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenantJenkinsRequest,
                deleteTenantJenkinsRequestMethod::execute);
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
                            log.warn("Idempotency was violated, object={}, {}", request.getTenantImage(),
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
    TenantLobbyRequest
     */

    @Override
    public Uni<GetTenantLobbyRequestResponse> getTenantLobbyRequest(
            @Valid final GetTenantLobbyRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantLobbyRequest,
                getTenantLobbyRequestMethod::execute);
    }

    @Override
    public Uni<FindTenantLobbyRequestResponse> findTenantLobbyRequest(
            @Valid final FindTenantLobbyRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::findTenantLobbyRequest,
                findTenantLobbyRequestMethod::execute);
    }

    @Override
    public Uni<ViewTenantLobbyRequestsResponse> viewTenantLobbyRequests(
            @Valid final ViewTenantLobbyRequestsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewTenantLobbyRequests,
                viewTenantLobbyRequestsMethod::execute);
    }

    @Override
    public Uni<SyncTenantLobbyRequestResponse> syncTenantLobbyRequest(
            @Valid final SyncTenantLobbyRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantLobbyRequest,
                syncTenantLobbyRequestMethod::execute);
    }

    @Override
    public Uni<SyncTenantLobbyRequestResponse> syncTenantLobbyRequestWithIdempotency(
            @Valid final SyncTenantLobbyRequestRequest request) {
        return syncTenantLobbyRequest(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", request.getTenantLobbyRequest(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncTenantLobbyRequestResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteTenantLobbyRequestResponse> deleteTenantLobbyRequest(
            @Valid final DeleteTenantLobbyRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenantLobbyRequest,
                deleteTenantLobbyRequestMethod::execute);
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
                            log.warn("Idempotency was violated, object={}, {}", request.getTenantLobbyRef(),
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
    TenantMatchmakerRequest
     */

    @Override
    public Uni<GetTenantMatchmakerRequestResponse> getTenantMatchmakerRequest(
            @Valid final GetTenantMatchmakerRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantMatchmakerRequest,
                getTenantMatchmakerRequestMethod::execute);
    }

    @Override
    public Uni<FindTenantMatchmakerRequestResponse> findTenantMatchmakerRequest(
            @Valid final FindTenantMatchmakerRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::findTenantMatchmakerRequest,
                findTenantMatchmakerRequestMethod::execute);
    }

    @Override
    public Uni<ViewTenantMatchmakerRequestsResponse> viewTenantMatchmakerRequests(
            @Valid final ViewTenantMatchmakerRequestsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewTenantMatchmakerRequests,
                viewTenantMatchmakerRequestsMethod::execute);
    }

    @Override
    public Uni<SyncTenantMatchmakerRequestResponse> syncTenantMatchmakerRequest(
            @Valid final SyncTenantMatchmakerRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantMatchmakerRequest,
                syncTenantMatchmakerRequestMethod::execute);
    }

    @Override
    public Uni<SyncTenantMatchmakerRequestResponse> syncTenantMatchmakerRequestWithIdempotency(
            @Valid final SyncTenantMatchmakerRequestRequest request) {
        return syncTenantMatchmakerRequest(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", request.getTenantMatchmakerRequest(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncTenantMatchmakerRequestResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteTenantMatchmakerRequestResponse> deleteTenantMatchmakerRequest(
            @Valid final DeleteTenantMatchmakerRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenantMatchmakerRequest,
                deleteTenantMatchmakerRequestMethod::execute);
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
                            log.warn("Idempotency was violated, object={}, {}", request.getTenantMatchmakerRef(),
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
