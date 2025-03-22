package com.omgservers.service.event;

import com.omgservers.service.event.body.internal.*;
import com.omgservers.service.event.body.module.client.*;
import com.omgservers.service.event.body.module.lobby.LobbyCreatedEventBodyModel;
import com.omgservers.service.event.body.module.lobby.LobbyDeletedEventBodyModel;
import com.omgservers.service.event.body.module.lobby.LobbyRuntimeRefCreatedEventBodyModel;
import com.omgservers.service.event.body.module.lobby.LobbyRuntimeRefDeletedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.*;
import com.omgservers.service.event.body.module.pool.*;
import com.omgservers.service.event.body.module.queue.QueueCreatedEventBodyModel;
import com.omgservers.service.event.body.module.queue.QueueDeletedEventBodyModel;
import com.omgservers.service.event.body.module.root.RootCreatedEventBodyModel;
import com.omgservers.service.event.body.module.root.RootDeletedEventBodyModel;
import com.omgservers.service.event.body.module.runtime.*;
import com.omgservers.service.event.body.module.tenant.*;
import com.omgservers.service.event.body.module.user.PlayerCreatedEventBodyModel;
import com.omgservers.service.event.body.module.user.PlayerDeletedEventBodyModel;
import com.omgservers.service.event.body.module.user.UserCreatedEventBodyModel;
import com.omgservers.service.event.body.module.user.UserDeletedEventBodyModel;
import com.omgservers.service.event.body.system.IndexCreatedEventBodyModel;
import com.omgservers.service.event.body.system.IndexDeletedEventBodyModel;
import com.omgservers.service.event.body.system.JobCreatedEventBodyModel;
import com.omgservers.service.event.body.system.JobDeletedEventBodyModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventQualifierEnum {
    // System
    INDEX_CREATED(IndexCreatedEventBodyModel.class),
    INDEX_DELETED(IndexDeletedEventBodyModel.class),
    JOB_CREATED(JobCreatedEventBodyModel.class),
    JOB_DELETED(JobDeletedEventBodyModel.class),
    // Shards
    ROOT_CREATED(RootCreatedEventBodyModel.class),
    ROOT_DELETED(RootDeletedEventBodyModel.class),
    POOL_CREATED(PoolCreatedEventBodyModel.class),
    POOL_DELETED(PoolDeletedEventBodyModel.class),
    POOL_SERVER_CREATED(PoolServerCreatedEventBodyModel.class),
    POOL_SERVER_DELETED(PoolServerDeletedEventBodyModel.class),
    POOL_CONTAINER_CREATED(PoolContainerCreatedEventBodyModel.class),
    POOL_CONTAINER_DELETED(PoolContainerDeletedEventBodyModel.class),
    POOL_REQUEST_CREATED(PoolRequestCreatedEventBodyModel.class),
    POOL_REQUEST_DELETED(PoolRequestDeletedEventBodyModel.class),
    TENANT_CREATED(TenantCreatedEventBodyModel.class),
    TENANT_DELETED(TenantDeletedEventBodyModel.class),
    TENANT_PROJECT_CREATED(TenantProjectCreatedEventBodyModel.class),
    TENANT_PROJECT_DELETED(TenantProjectDeletedEventBodyModel.class),
    TENANT_VERSION_CREATED(TenantVersionCreatedEventBodyModel.class),
    TENANT_VERSION_DELETED(TenantVersionDeletedEventBodyModel.class),
    TENANT_STAGE_CREATED(TenantStageCreatedEventBodyModel.class),
    TENANT_STAGE_DELETED(TenantStageDeletedEventBodyModel.class),
    TENANT_DEPLOYMENT_CREATED(TenantDeploymentCreatedEventBodyModel.class),
    TENANT_DEPLOYMENT_DELETED(TenantDeploymentDeletedEventBodyModel.class),
    TENANT_FILES_ARCHIVE_CREATED(TenantFilesArchiveCreatedEventBodyModel.class),
    TENANT_FILES_ARCHIVE_DELETED(TenantFilesArchiveDeletedEventBodyModel.class),
    TENANT_BUILD_REQUEST_CREATED(TenantBuildRequestCreatedEventBodyModel.class),
    TENANT_BUILD_REQUEST_DELETED(TenantBuildRequestDeletedEventBodyModel.class),
    TENANT_IMAGE_CREATED(TenantImageCreatedEventBodyModel.class),
    TENANT_IMAGE_DELETED(TenantImageDeletedEventBodyModel.class),
    TENANT_LOBBY_REQUEST_CREATED(TenantLobbyRequestCreatedEventBodyModel.class),
    TENANT_LOBBY_REQUEST_DELETED(TenantLobbyRequestDeletedEventBodyModel.class),
    TENANT_LOBBY_REF_CREATED(TenantLobbyRefCreatedEventBodyModel.class),
    TENANT_LOBBY_REF_DELETED(TenantLobbyRefDeletedEventBodyModel.class),
    TENANT_MATCHMAKER_REQUEST_CREATED(TenantMatchmakerRequestCreatedEventBodyModel.class),
    TENANT_MATCHMAKER_REQUEST_DELETED(TenantMatchmakerRequestDeletedEventBodyModel.class),
    TENANT_MATCHMAKER_REF_CREATED(TenantMatchmakerRefCreatedEventBodyModel.class),
    TENANT_MATCHMAKER_REF_DELETED(TenantMatchmakerRefDeletedEventBodyModel.class),
    USER_CREATED(UserCreatedEventBodyModel.class),
    USER_DELETED(UserDeletedEventBodyModel.class),
    PLAYER_CREATED(PlayerCreatedEventBodyModel.class),
    PLAYER_DELETED(PlayerDeletedEventBodyModel.class),
    CLIENT_CREATED(ClientCreatedEventBodyModel.class),
    CLIENT_DELETED(ClientDeletedEventBodyModel.class),
    CLIENT_RUNTIME_REF_CREATED(ClientRuntimeRefCreatedEventBodyModel.class),
    CLIENT_RUNTIME_REF_DELETED(ClientRuntimeRefDeletedEventBodyModel.class),
    CLIENT_MATCHMAKER_REF_CREATED(ClientMatchmakerRefCreatedEventBodyModel.class),
    CLIENT_MATCHMAKER_REF_DELETED(ClientMatchmakerRefDeletedEventBodyModel.class),
    QUEUE_CREATED(QueueCreatedEventBodyModel.class),
    QUEUE_DELETED(QueueDeletedEventBodyModel.class),
    LOBBY_CREATED(LobbyCreatedEventBodyModel.class),
    LOBBY_DELETED(LobbyDeletedEventBodyModel.class),
    LOBBY_RUNTIME_REF_CREATED(LobbyRuntimeRefCreatedEventBodyModel.class),
    LOBBY_RUNTIME_REF_DELETED(LobbyRuntimeRefDeletedEventBodyModel.class),
    MATCHMAKER_CREATED(MatchmakerCreatedEventBodyModel.class),
    MATCHMAKER_DELETED(MatchmakerDeletedEventBodyModel.class),
    MATCHMAKER_REQUEST_CREATED(MatchmakerRequestCreatedEventBodyModel.class),
    MATCHMAKER_REQUEST_DELETED(MatchmakerRequestDeletedEventBodyModel.class),
    MATCHMAKER_ASSIGNMENT_CREATED(MatchmakerAssignmentCreatedEventBodyModel.class),
    MATCHMAKER_ASSIGNMENT_DELETED(MatchmakerAssignmentDeletedEventBodyModel.class),
    MATCHMAKER_MATCH_CREATED(MatchmakerMatchCreatedEventBodyModel.class),
    MATCHMAKER_MATCH_DELETED(MatchmakerMatchDeletedEventBodyModel.class),
    MATCHMAKER_MATCH_ASSIGNMENT_CREATED(MatchmakerMatchAssignmentCreatedEventBodyModel.class),
    MATCHMAKER_MATCH_ASSIGNMENT_DELETED(MatchmakerMatchAssignmentDeletedEventBodyModel.class),
    MATCHMAKER_MATCH_RUNTIME_REF_CREATED(MatchmakerMatchRuntimeRefCreatedEventBodyModel.class),
    MATCHMAKER_MATCH_RUNTIME_REF_DELETED(MatchmakerMatchRuntimeRefDeletedEventBodyModel.class),
    RUNTIME_CREATED(RuntimeCreatedEventBodyModel.class),
    RUNTIME_DELETED(RuntimeDeletedEventBodyModel.class),
    RUNTIME_ASSIGNMENT_CREATED(RuntimeAssignmentCreatedEventBodyModel.class),
    RUNTIME_ASSIGNMENT_DELETED(RuntimeAssignmentDeletedEventBodyModel.class),
    RUNTIME_POOL_CONTAINER_REF_CREATED(RuntimePoolContainerRefCreatedEventBodyModel.class),
    RUNTIME_POOL_CONTAINER_REF_DELETED(RuntimePoolContainerRefDeletedEventBodyModel.class),
    // Internal
    VERSION_BUILDING_REQUESTED(VersionBuildingRequestedEventBodyModel.class),
    VERSION_BUILDING_FAILED(VersionBuildingFailedEventBodyModel.class),
    VERSION_BUILDING_FINISHED(VersionBuildingFinishedEventBodyModel.class),
    RUNTIME_DEPLOYMENT_REQUESTED(RuntimeDeploymentRequestedEventBodyModel.class),
    INACTIVE_CLIENT_DETECTED(InactiveClientDetectedEventBodyModel.class),
    FAILED_CLIENT_DETECTED(FailedClientDetectedEventBodyModel.class),
    INACTIVE_RUNTIME_DETECTED(InactiveRuntimeDetectedEventBodyModel.class);

    final Class<? extends EventBodyModel> bodyClass;
}
