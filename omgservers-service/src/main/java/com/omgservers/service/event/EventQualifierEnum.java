package com.omgservers.service.event;

import com.omgservers.service.event.body.internal.DockerRegistryEventReceivedEventBodyModel;
import com.omgservers.service.event.body.internal.FailedClientDetectedEventBodyModel;
import com.omgservers.service.event.body.internal.InactiveClientDetectedEventBodyModel;
import com.omgservers.service.event.body.internal.InactiveRuntimeDetectedEventBodyModel;
import com.omgservers.service.event.body.internal.LobbyAssignmentRequestedEventBodyModel;
import com.omgservers.service.event.body.internal.MatchmakerAssignmentRequestedEventBodyModel;
import com.omgservers.service.event.body.internal.RuntimeDeploymentRequestedEventBodyModel;
import com.omgservers.service.event.body.internal.VersionBuildingFailedEventBodyModel;
import com.omgservers.service.event.body.internal.VersionBuildingFinishedEventBodyModel;
import com.omgservers.service.event.body.internal.VersionBuildingRequestedEventBodyModel;
import com.omgservers.service.event.body.module.client.ClientCreatedEventBodyModel;
import com.omgservers.service.event.body.module.client.ClientDeletedEventBodyModel;
import com.omgservers.service.event.body.module.client.ClientMatchmakerRefCreatedEventBodyModel;
import com.omgservers.service.event.body.module.client.ClientMatchmakerRefDeletedEventBodyModel;
import com.omgservers.service.event.body.module.client.ClientRuntimeRefCreatedEventBodyModel;
import com.omgservers.service.event.body.module.client.ClientRuntimeRefDeletedEventBodyModel;
import com.omgservers.service.event.body.module.lobby.LobbyCreatedEventBodyModel;
import com.omgservers.service.event.body.module.lobby.LobbyDeletedEventBodyModel;
import com.omgservers.service.event.body.module.lobby.LobbyRuntimeRefCreatedEventBodyModel;
import com.omgservers.service.event.body.module.lobby.LobbyRuntimeRefDeletedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerAssignmentCreatedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerAssignmentDeletedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerCreatedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerDeletedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchAssignmentCreatedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchAssignmentDeletedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchCreatedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchDeletedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchRuntimeRefCreatedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchRuntimeRefDeletedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerRequestCreatedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerRequestDeletedEventBodyModel;
import com.omgservers.service.event.body.module.pool.PoolContainerCreatedEventBodyModel;
import com.omgservers.service.event.body.module.pool.PoolContainerDeletedEventBodyModel;
import com.omgservers.service.event.body.module.pool.PoolCreatedEventBodyModel;
import com.omgservers.service.event.body.module.pool.PoolDeletedEventBodyModel;
import com.omgservers.service.event.body.module.pool.PoolRequestCreatedEventBodyModel;
import com.omgservers.service.event.body.module.pool.PoolRequestDeletedEventBodyModel;
import com.omgservers.service.event.body.module.pool.PoolServerCreatedEventBodyModel;
import com.omgservers.service.event.body.module.pool.PoolServerDeletedEventBodyModel;
import com.omgservers.service.event.body.module.root.RootCreatedEventBodyModel;
import com.omgservers.service.event.body.module.root.RootDeletedEventBodyModel;
import com.omgservers.service.event.body.module.runtime.RuntimeAssignmentCreatedEventBodyModel;
import com.omgservers.service.event.body.module.runtime.RuntimeAssignmentDeletedEventBodyModel;
import com.omgservers.service.event.body.module.runtime.RuntimeCreatedEventBodyModel;
import com.omgservers.service.event.body.module.runtime.RuntimeDeletedEventBodyModel;
import com.omgservers.service.event.body.module.runtime.RuntimePoolContainerRefCreatedEventBodyModel;
import com.omgservers.service.event.body.module.runtime.RuntimePoolContainerRefDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantBuildRequestCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantBuildRequestDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantDeploymentCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantDeploymentDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantFilesArchiveCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantFilesArchiveDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantImageCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantImageDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantLobbyRefCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantLobbyRefDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantLobbyRequestCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantLobbyRequestDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantMatchmakerRefCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantMatchmakerRefDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantMatchmakerRequestCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantMatchmakerRequestDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantProjectCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantProjectDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantStageCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantStageDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantVersionCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantVersionDeletedEventBodyModel;
import com.omgservers.service.event.body.module.user.PlayerCreatedEventBodyModel;
import com.omgservers.service.event.body.module.user.PlayerDeletedEventBodyModel;
import com.omgservers.service.event.body.module.user.UserCreatedEventBodyModel;
import com.omgservers.service.event.body.module.user.UserDeletedEventBodyModel;
import com.omgservers.service.event.body.system.IndexCreatedEventBodyModel;
import com.omgservers.service.event.body.system.IndexDeletedEventBodyModel;
import com.omgservers.service.event.body.system.JobCreatedEventBodyModel;
import com.omgservers.service.event.body.system.JobDeletedEventBodyModel;

public enum EventQualifierEnum {
    // System
    INDEX_CREATED(IndexCreatedEventBodyModel.class, true),
    INDEX_DELETED(IndexDeletedEventBodyModel.class, true),
    JOB_CREATED(JobCreatedEventBodyModel.class, false),
    JOB_DELETED(JobDeletedEventBodyModel.class, false),
    // Module
    ROOT_CREATED(RootCreatedEventBodyModel.class, false),
    ROOT_DELETED(RootDeletedEventBodyModel.class, false),
    POOL_CREATED(PoolCreatedEventBodyModel.class, true),
    POOL_DELETED(PoolDeletedEventBodyModel.class, true),
    POOL_SERVER_CREATED(PoolServerCreatedEventBodyModel.class, true),
    POOL_SERVER_DELETED(PoolServerDeletedEventBodyModel.class, true),
    POOL_CONTAINER_CREATED(PoolContainerCreatedEventBodyModel.class, true),
    POOL_CONTAINER_DELETED(PoolContainerDeletedEventBodyModel.class, true),
    POOL_REQUEST_CREATED(PoolRequestCreatedEventBodyModel.class, false),
    POOL_REQUEST_DELETED(PoolRequestDeletedEventBodyModel.class, false),
    TENANT_CREATED(TenantCreatedEventBodyModel.class, true),
    TENANT_DELETED(TenantDeletedEventBodyModel.class, true),
    TENANT_PROJECT_CREATED(TenantProjectCreatedEventBodyModel.class, true),
    TENANT_PROJECT_DELETED(TenantProjectDeletedEventBodyModel.class, true),
    TENANT_VERSION_CREATED(TenantVersionCreatedEventBodyModel.class, true),
    TENANT_VERSION_DELETED(TenantVersionDeletedEventBodyModel.class, true),
    TENANT_STAGE_CREATED(TenantStageCreatedEventBodyModel.class, true),
    TENANT_STAGE_DELETED(TenantStageDeletedEventBodyModel.class, true),
    TENANT_DEPLOYMENT_CREATED(TenantDeploymentCreatedEventBodyModel.class, true),
    TENANT_DEPLOYMENT_DELETED(TenantDeploymentDeletedEventBodyModel.class, true),
    TENANT_FILES_ARCHIVE_CREATED(TenantFilesArchiveCreatedEventBodyModel.class, true),
    TENANT_FILES_ARCHIVE_DELETED(TenantFilesArchiveDeletedEventBodyModel.class, true),
    TENANT_BUILD_REQUEST_CREATED(TenantBuildRequestCreatedEventBodyModel.class, true),
    TENANT_BUILD_REQUEST_DELETED(TenantBuildRequestDeletedEventBodyModel.class, true),
    TENANT_IMAGE_CREATED(TenantImageCreatedEventBodyModel.class, true),
    TENANT_IMAGE_DELETED(TenantImageDeletedEventBodyModel.class, true),
    TENANT_LOBBY_REQUEST_CREATED(TenantLobbyRequestCreatedEventBodyModel.class, true),
    TENANT_LOBBY_REQUEST_DELETED(TenantLobbyRequestDeletedEventBodyModel.class, true),
    TENANT_LOBBY_REF_CREATED(TenantLobbyRefCreatedEventBodyModel.class, false),
    TENANT_LOBBY_REF_DELETED(TenantLobbyRefDeletedEventBodyModel.class, false),
    TENANT_MATCHMAKER_REQUEST_CREATED(TenantMatchmakerRequestCreatedEventBodyModel.class, true),
    TENANT_MATCHMAKER_REQUEST_DELETED(TenantMatchmakerRequestDeletedEventBodyModel.class, true),
    TENANT_MATCHMAKER_REF_CREATED(TenantMatchmakerRefCreatedEventBodyModel.class, false),
    TENANT_MATCHMAKER_REF_DELETED(TenantMatchmakerRefDeletedEventBodyModel.class, false),
    USER_CREATED(UserCreatedEventBodyModel.class, true),
    USER_DELETED(UserDeletedEventBodyModel.class, true),
    PLAYER_CREATED(PlayerCreatedEventBodyModel.class, true),
    PLAYER_DELETED(PlayerDeletedEventBodyModel.class, true),
    CLIENT_CREATED(ClientCreatedEventBodyModel.class, true),
    CLIENT_DELETED(ClientDeletedEventBodyModel.class, true),
    CLIENT_RUNTIME_REF_CREATED(ClientRuntimeRefCreatedEventBodyModel.class, false),
    CLIENT_RUNTIME_REF_DELETED(ClientRuntimeRefDeletedEventBodyModel.class, false),
    CLIENT_MATCHMAKER_REF_CREATED(ClientMatchmakerRefCreatedEventBodyModel.class, false),
    CLIENT_MATCHMAKER_REF_DELETED(ClientMatchmakerRefDeletedEventBodyModel.class, false),
    LOBBY_CREATED(LobbyCreatedEventBodyModel.class, true),
    LOBBY_DELETED(LobbyDeletedEventBodyModel.class, true),
    LOBBY_RUNTIME_REF_CREATED(LobbyRuntimeRefCreatedEventBodyModel.class, false),
    LOBBY_RUNTIME_REF_DELETED(LobbyRuntimeRefDeletedEventBodyModel.class, false),
    MATCHMAKER_CREATED(MatchmakerCreatedEventBodyModel.class, true),
    MATCHMAKER_DELETED(MatchmakerDeletedEventBodyModel.class, true),
    MATCHMAKER_REQUEST_CREATED(MatchmakerRequestCreatedEventBodyModel.class, true),
    MATCHMAKER_REQUEST_DELETED(MatchmakerRequestDeletedEventBodyModel.class, true),
    MATCHMAKER_ASSIGNMENT_CREATED(MatchmakerAssignmentCreatedEventBodyModel.class, true),
    MATCHMAKER_ASSIGNMENT_DELETED(MatchmakerAssignmentDeletedEventBodyModel.class, true),
    MATCHMAKER_MATCH_CREATED(MatchmakerMatchCreatedEventBodyModel.class, true),
    MATCHMAKER_MATCH_DELETED(MatchmakerMatchDeletedEventBodyModel.class, true),
    MATCHMAKER_MATCH_ASSIGNMENT_CREATED(MatchmakerMatchAssignmentCreatedEventBodyModel.class, true),
    MATCHMAKER_MATCH_ASSIGNMENT_DELETED(MatchmakerMatchAssignmentDeletedEventBodyModel.class, true),
    MATCHMAKER_MATCH_RUNTIME_REF_CREATED(MatchmakerMatchRuntimeRefCreatedEventBodyModel.class, false),
    MATCHMAKER_MATCH_RUNTIME_REF_DELETED(MatchmakerMatchRuntimeRefDeletedEventBodyModel.class, false),
    RUNTIME_CREATED(RuntimeCreatedEventBodyModel.class, true),
    RUNTIME_DELETED(RuntimeDeletedEventBodyModel.class, true),
    RUNTIME_ASSIGNMENT_CREATED(RuntimeAssignmentCreatedEventBodyModel.class, true),
    RUNTIME_ASSIGNMENT_DELETED(RuntimeAssignmentDeletedEventBodyModel.class, true),
    RUNTIME_POOL_CONTAINER_REF_CREATED(RuntimePoolContainerRefCreatedEventBodyModel.class, false),
    RUNTIME_POOL_CONTAINER_REF_DELETED(RuntimePoolContainerRefDeletedEventBodyModel.class, false),
    // Internal
    DOCKER_REGISTRY_EVENT_RECEIVED(DockerRegistryEventReceivedEventBodyModel.class, false),
    VERSION_BUILDING_REQUESTED(VersionBuildingRequestedEventBodyModel.class, false),
    VERSION_BUILDING_FAILED(VersionBuildingFailedEventBodyModel.class, false),
    VERSION_BUILDING_FINISHED(VersionBuildingFinishedEventBodyModel.class, false),
    RUNTIME_DEPLOYMENT_REQUESTED(RuntimeDeploymentRequestedEventBodyModel.class, false),
    INACTIVE_CLIENT_DETECTED(InactiveClientDetectedEventBodyModel.class, false),
    FAILED_CLIENT_DETECTED(FailedClientDetectedEventBodyModel.class, false),
    INACTIVE_RUNTIME_DETECTED(InactiveRuntimeDetectedEventBodyModel.class, false),
    LOBBY_ASSIGNMENT_REQUESTED(LobbyAssignmentRequestedEventBodyModel.class, false),
    MATCHMAKER_ASSIGNMENT_REQUESTED(MatchmakerAssignmentRequestedEventBodyModel.class, false);

    final Class<? extends EventBodyModel> bodyClass;
    final boolean forward;

    EventQualifierEnum(final Class<? extends EventBodyModel> bodyClass,
                       final Boolean forward) {
        this.bodyClass = bodyClass;
        this.forward = forward;
    }

    public Class<? extends EventBodyModel> getBodyClass() {
        return bodyClass;
    }

    public boolean isForward() {
        return forward;
    }
}
