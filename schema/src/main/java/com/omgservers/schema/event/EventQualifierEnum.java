package com.omgservers.schema.event;

import com.omgservers.schema.event.body.internal.ClientMessageReceivedEventBodyModel;
import com.omgservers.schema.event.body.internal.DockerRegistryEventReceivedEventBodyModel;
import com.omgservers.schema.event.body.internal.InactiveClientDetectedEventBodyModel;
import com.omgservers.schema.event.body.internal.InactiveRuntimeDetectedEventBodyModel;
import com.omgservers.schema.event.body.internal.LobbyAssignmentRequestedEventBodyModel;
import com.omgservers.schema.event.body.internal.MatchmakerAssignmentRequestedEventBodyModel;
import com.omgservers.schema.event.body.internal.RuntimeDeploymentRequestedEventBodyModel;
import com.omgservers.schema.event.body.internal.VersionBuildingCheckingRequestedEventBodyModel;
import com.omgservers.schema.event.body.internal.VersionBuildingFailedEventBodyModel;
import com.omgservers.schema.event.body.internal.VersionBuildingFinishedEventBodyModel;
import com.omgservers.schema.event.body.internal.VersionBuildingRequestedEventBodyModel;
import com.omgservers.schema.event.body.internal.VersionDeploymentRequestedEventBodyModel;
import com.omgservers.schema.event.body.module.client.ClientCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.client.ClientDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.client.ClientMatchmakerRefCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.client.ClientMatchmakerRefDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.client.ClientRuntimeRefCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.client.ClientRuntimeRefDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.lobby.LobbyCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.lobby.LobbyDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.lobby.LobbyRuntimeRefCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.lobby.LobbyRuntimeRefDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.matchmaker.MatchmakerAssignmentCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.matchmaker.MatchmakerAssignmentDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.matchmaker.MatchmakerCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.matchmaker.MatchmakerDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.matchmaker.MatchmakerMatchClientCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.matchmaker.MatchmakerMatchClientDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.matchmaker.MatchmakerMatchCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.matchmaker.MatchmakerMatchDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.matchmaker.MatchmakerMatchRuntimeRefCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.matchmaker.MatchmakerMatchRuntimeRefDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.matchmaker.MatchmakerRequestCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.matchmaker.MatchmakerRequestDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.pool.PoolCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.pool.PoolDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.pool.PoolRequestCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.pool.PoolRequestDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.pool.PoolServerContainerCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.pool.PoolServerContainerDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.pool.PoolServerCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.pool.PoolServerDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.root.RootCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.root.RootDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.runtime.RuntimeAssignmentCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.runtime.RuntimeAssignmentDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.runtime.RuntimeCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.runtime.RuntimeDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.runtime.RuntimePoolServerContainerRefCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.runtime.RuntimePoolServerContainerRefDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.ProjectCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.ProjectDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.StageCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.StageDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.TenantCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.TenantDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.VersionCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.VersionDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.VersionImageRefCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.VersionImageRefDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.VersionJenkinsRequestCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.VersionJenkinsRequestDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.VersionLobbyRefCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.VersionLobbyRefDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.VersionLobbyRequestCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.VersionLobbyRequestDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.VersionMatchmakerRefCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.VersionMatchmakerRefDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.VersionMatchmakerRequestCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.tenant.VersionMatchmakerRequestDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.user.PlayerCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.user.PlayerDeletedEventBodyModel;
import com.omgservers.schema.event.body.module.user.UserCreatedEventBodyModel;
import com.omgservers.schema.event.body.module.user.UserDeletedEventBodyModel;
import com.omgservers.schema.event.body.system.IndexCreatedEventBodyModel;
import com.omgservers.schema.event.body.system.IndexDeletedEventBodyModel;
import com.omgservers.schema.event.body.system.JobCreatedEventBodyModel;
import com.omgservers.schema.event.body.system.JobDeletedEventBodyModel;
import com.omgservers.schema.event.body.system.ServiceAccountCreatedEventBodyModel;
import com.omgservers.schema.event.body.system.ServiceAccountDeletedEventBodyModel;

public enum EventQualifierEnum {
    // System
    INDEX_CREATED(IndexCreatedEventBodyModel.class, true),
    INDEX_DELETED(IndexDeletedEventBodyModel.class, true),
    SERVICE_ACCOUNT_CREATED(ServiceAccountCreatedEventBodyModel.class, true),
    SERVICE_ACCOUNT_DELETED(ServiceAccountDeletedEventBodyModel.class, true),
    JOB_CREATED(JobCreatedEventBodyModel.class, false),
    JOB_DELETED(JobDeletedEventBodyModel.class, false),
    // Module
    ROOT_CREATED(RootCreatedEventBodyModel.class, false),
    ROOT_DELETED(RootDeletedEventBodyModel.class, false),
    POOL_CREATED(PoolCreatedEventBodyModel.class, true),
    POOL_DELETED(PoolDeletedEventBodyModel.class, true),
    POOL_SERVER_CREATED(PoolServerCreatedEventBodyModel.class, true),
    POOL_SERVER_DELETED(PoolServerDeletedEventBodyModel.class, true),
    POOL_REQUEST_CREATED(PoolRequestCreatedEventBodyModel.class, false),
    POOL_REQUEST_DELETED(PoolRequestDeletedEventBodyModel.class, false),
    POOL_SERVER_CONTAINER_CREATED(PoolServerContainerCreatedEventBodyModel.class, true),
    POOL_SERVER_CONTAINER_DELETED(PoolServerContainerDeletedEventBodyModel.class, true),
    TENANT_CREATED(TenantCreatedEventBodyModel.class, true),
    TENANT_DELETED(TenantDeletedEventBodyModel.class, true),
    PROJECT_CREATED(ProjectCreatedEventBodyModel.class, true),
    PROJECT_DELETED(ProjectDeletedEventBodyModel.class, true),
    STAGE_CREATED(StageCreatedEventBodyModel.class, true),
    STAGE_DELETED(StageDeletedEventBodyModel.class, true),
    VERSION_JENKINS_REQUEST_CREATED(VersionJenkinsRequestCreatedEventBodyModel.class, true),
    VERSION_JENKINS_REQUEST_DELETED(VersionJenkinsRequestDeletedEventBodyModel.class, true),
    VERSION_IMAGE_REF_CREATED(VersionImageRefCreatedEventBodyModel.class, true),
    VERSION_IMAGE_REF_DELETED(VersionImageRefDeletedEventBodyModel.class, true),
    VERSION_LOBBY_REQUEST_CREATED(VersionLobbyRequestCreatedEventBodyModel.class, true),
    VERSION_LOBBY_REQUEST_DELETED(VersionLobbyRequestDeletedEventBodyModel.class, true),
    VERSION_LOBBY_REF_CREATED(VersionLobbyRefCreatedEventBodyModel.class, false),
    VERSION_LOBBY_REF_DELETED(VersionLobbyRefDeletedEventBodyModel.class, false),
    VERSION_MATCHMAKER_REQUEST_CREATED(VersionMatchmakerRequestCreatedEventBodyModel.class, true),
    VERSION_MATCHMAKER_REQUEST_DELETED(VersionMatchmakerRequestDeletedEventBodyModel.class, true),
    VERSION_MATCHMAKER_REF_CREATED(VersionMatchmakerRefCreatedEventBodyModel.class, false),
    VERSION_MATCHMAKER_REF_DELETED(VersionMatchmakerRefDeletedEventBodyModel.class, false),
    VERSION_CREATED(VersionCreatedEventBodyModel.class, true),
    VERSION_DELETED(VersionDeletedEventBodyModel.class, true),
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
    MATCHMAKER_MATCH_CREATED(MatchmakerMatchCreatedEventBodyModel.class, true),
    MATCHMAKER_MATCH_DELETED(MatchmakerMatchDeletedEventBodyModel.class, true),
    MATCHMAKER_MATCH_CLIENT_CREATED(MatchmakerMatchClientCreatedEventBodyModel.class, true),
    MATCHMAKER_MATCH_CLIENT_DELETED(MatchmakerMatchClientDeletedEventBodyModel.class, true),
    MATCHMAKER_MATCH_RUNTIME_REF_CREATED(MatchmakerMatchRuntimeRefCreatedEventBodyModel.class, false),
    MATCHMAKER_MATCH_RUNTIME_REF_DELETED(MatchmakerMatchRuntimeRefDeletedEventBodyModel.class, false),
    MATCHMAKER_REQUEST_CREATED(MatchmakerRequestCreatedEventBodyModel.class, true),
    MATCHMAKER_REQUEST_DELETED(MatchmakerRequestDeletedEventBodyModel.class, true),
    MATCHMAKER_ASSIGNMENT_CREATED(MatchmakerAssignmentCreatedEventBodyModel.class, true),
    MATCHMAKER_ASSIGNMENT_DELETED(MatchmakerAssignmentDeletedEventBodyModel.class, true),
    RUNTIME_CREATED(RuntimeCreatedEventBodyModel.class, true),
    RUNTIME_DELETED(RuntimeDeletedEventBodyModel.class, true),
    RUNTIME_ASSIGNMENT_CREATED(RuntimeAssignmentCreatedEventBodyModel.class, true),
    RUNTIME_ASSIGNMENT_DELETED(RuntimeAssignmentDeletedEventBodyModel.class, true),
    RUNTIME_POOL_SERVER_CONTAINER_REF_CREATED(RuntimePoolServerContainerRefCreatedEventBodyModel.class, false),
    RUNTIME_POOL_SERVER_CONTAINER_REF_DELETED(RuntimePoolServerContainerRefDeletedEventBodyModel.class, false),
    // Internal
    DOCKER_REGISTRY_EVENT_RECEIVED(DockerRegistryEventReceivedEventBodyModel.class, false),
    VERSION_BUILDING_REQUESTED(VersionBuildingRequestedEventBodyModel.class, false),
    VERSION_BUILDING_CHECKING_REQUESTED(VersionBuildingCheckingRequestedEventBodyModel.class, false),
    VERSION_BUILDING_FAILED(VersionBuildingFailedEventBodyModel.class, false),
    VERSION_BUILDING_FINISHED(VersionBuildingFinishedEventBodyModel.class, false),
    VERSION_DEPLOYMENT_REQUESTED(VersionDeploymentRequestedEventBodyModel.class, false),
    RUNTIME_DEPLOYMENT_REQUESTED(RuntimeDeploymentRequestedEventBodyModel.class, false),
    CLIENT_MESSAGE_RECEIVED(ClientMessageReceivedEventBodyModel.class, false),
    INACTIVE_CLIENT_DETECTED(InactiveClientDetectedEventBodyModel.class, false),
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
