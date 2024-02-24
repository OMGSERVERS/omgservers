package com.omgservers.model.event;

import com.omgservers.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.model.event.body.ClientDeletedEventBodyModel;
import com.omgservers.model.event.body.ClientMessageReceivedEventBodyModel;
import com.omgservers.model.event.body.ClientRuntimeRefCreatedEventBodyModel;
import com.omgservers.model.event.body.ClientRuntimeRefDeletedEventBodyModel;
import com.omgservers.model.event.body.ContainerCreatedEventBodyModel;
import com.omgservers.model.event.body.ContainerDeletedEventBodyModel;
import com.omgservers.model.event.body.InactiveClientDetectedEventBodyModel;
import com.omgservers.model.event.body.IndexCreatedEventBodyModel;
import com.omgservers.model.event.body.IndexDeletedEventBodyModel;
import com.omgservers.model.event.body.LobbyCreatedEventBodyModel;
import com.omgservers.model.event.body.LobbyDeletedEventBodyModel;
import com.omgservers.model.event.body.LobbyRuntimeRefCreatedEventBodyModel;
import com.omgservers.model.event.body.LobbyRuntimeRefDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchClientCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchClientDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.model.event.body.MatchRuntimeRefCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchRuntimeRefDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerMessageReceivedEventBodyModel;
import com.omgservers.model.event.body.PlayerCreatedEventBodyModel;
import com.omgservers.model.event.body.PlayerDeletedEventBodyModel;
import com.omgservers.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.model.event.body.ProjectDeletedEventBodyModel;
import com.omgservers.model.event.body.RequestCreatedEventBodyModel;
import com.omgservers.model.event.body.RuntimeClientCreatedEventBodyModel;
import com.omgservers.model.event.body.RuntimeClientDeletedEventBodyModel;
import com.omgservers.model.event.body.RuntimeCreatedEventBodyModel;
import com.omgservers.model.event.body.RuntimeDeletedEventBodyModel;
import com.omgservers.model.event.body.RuntimeJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.model.event.body.RuntimeLobbyCreatedEventBodyModel;
import com.omgservers.model.event.body.RuntimeLobbyDeletedEventBodyModel;
import com.omgservers.model.event.body.RuntimeMatchCreatedEventBodyModel;
import com.omgservers.model.event.body.RuntimeMatchDeletedEventBodyModel;
import com.omgservers.model.event.body.ServiceAccountCreatedEventBodyModel;
import com.omgservers.model.event.body.ServiceAccountDeletedEventBodyModel;
import com.omgservers.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.model.event.body.StageDeletedEventBodyModel;
import com.omgservers.model.event.body.StageJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.model.event.body.TenantCreatedEventBodyModel;
import com.omgservers.model.event.body.TenantDeletedEventBodyModel;
import com.omgservers.model.event.body.UserCreatedEventBodyModel;
import com.omgservers.model.event.body.UserDeletedEventBodyModel;
import com.omgservers.model.event.body.VersionCreatedEventBodyModel;
import com.omgservers.model.event.body.VersionDeletedEventBodyModel;
import com.omgservers.model.event.body.VersionLobbyRefCreatedEventBodyModel;
import com.omgservers.model.event.body.VersionLobbyRefDeletedEventBodyModel;
import com.omgservers.model.event.body.VersionLobbyRequestCreatedEventBodyModel;
import com.omgservers.model.event.body.VersionLobbyRequestDeletedEventBodyModel;
import com.omgservers.model.event.body.VersionMatchmakerRefCreatedEventBodyModel;
import com.omgservers.model.event.body.VersionMatchmakerRefDeletedEventBodyModel;
import com.omgservers.model.event.body.VersionMatchmakerRequestCreatedEventBodyModel;
import com.omgservers.model.event.body.VersionMatchmakerRequestDeletedEventBodyModel;

public enum EventQualifierEnum {
    // System
    INDEX_CREATED(IndexCreatedEventBodyModel.class),
    INDEX_DELETED(IndexDeletedEventBodyModel.class),
    SERVICE_ACCOUNT_CREATED(ServiceAccountCreatedEventBodyModel.class),
    SERVICE_ACCOUNT_DELETED(ServiceAccountDeletedEventBodyModel.class),
    CONTAINER_CREATED(ContainerCreatedEventBodyModel.class),
    CONTAINER_DELETED(ContainerDeletedEventBodyModel.class),
    // Shard
    TENANT_CREATED(TenantCreatedEventBodyModel.class),
    TENANT_DELETED(TenantDeletedEventBodyModel.class),
    PROJECT_CREATED(ProjectCreatedEventBodyModel.class),
    PROJECT_DELETED(ProjectDeletedEventBodyModel.class),
    STAGE_CREATED(StageCreatedEventBodyModel.class),
    STAGE_DELETED(StageDeletedEventBodyModel.class),
    VERSION_LOBBY_REQUEST_CREATED(VersionLobbyRequestCreatedEventBodyModel.class),
    VERSION_LOBBY_REQUEST_DELETED(VersionLobbyRequestDeletedEventBodyModel.class),
    VERSION_LOBBY_REF_CREATED(VersionLobbyRefCreatedEventBodyModel.class),
    VERSION_LOBBY_REF_DELETED(VersionLobbyRefDeletedEventBodyModel.class),
    VERSION_MATCHMAKER_REQUEST_CREATED(VersionMatchmakerRequestCreatedEventBodyModel.class),
    VERSION_MATCHMAKER_REQUEST_DELETED(VersionMatchmakerRequestDeletedEventBodyModel.class),
    VERSION_MATCHMAKER_REF_CREATED(VersionMatchmakerRefCreatedEventBodyModel.class),
    VERSION_MATCHMAKER_REF_DELETED(VersionMatchmakerRefDeletedEventBodyModel.class),
    VERSION_CREATED(VersionCreatedEventBodyModel.class),
    VERSION_DELETED(VersionDeletedEventBodyModel.class),
    USER_CREATED(UserCreatedEventBodyModel.class),
    USER_DELETED(UserDeletedEventBodyModel.class),
    PLAYER_CREATED(PlayerCreatedEventBodyModel.class),
    PLAYER_DELETED(PlayerDeletedEventBodyModel.class),
    CLIENT_CREATED(ClientCreatedEventBodyModel.class),
    CLIENT_DELETED(ClientDeletedEventBodyModel.class),
    CLIENT_RUNTIME_REF_CREATED(ClientRuntimeRefCreatedEventBodyModel.class),
    CLIENT_RUNTIME_REF_DELETED(ClientRuntimeRefDeletedEventBodyModel.class),
    LOBBY_CREATED(LobbyCreatedEventBodyModel.class),
    LOBBY_DELETED(LobbyDeletedEventBodyModel.class),
    LOBBY_RUNTIME_REF_CREATED(LobbyRuntimeRefCreatedEventBodyModel.class),
    LOBBY_RUNTIME_REF_DELETED(LobbyRuntimeRefDeletedEventBodyModel.class),
    MATCHMAKER_CREATED(MatchmakerCreatedEventBodyModel.class),
    MATCHMAKER_DELETED(MatchmakerDeletedEventBodyModel.class),
    MATCH_CREATED(MatchCreatedEventBodyModel.class),
    MATCH_DELETED(MatchDeletedEventBodyModel.class),
    MATCH_CLIENT_CREATED(MatchClientCreatedEventBodyModel.class),
    MATCH_CLIENT_DELETED(MatchClientDeletedEventBodyModel.class),
    MATCH_RUNTIME_REF_CREATED(MatchRuntimeRefCreatedEventBodyModel.class),
    MATCH_RUNTIME_REF_DELETED(MatchRuntimeRefDeletedEventBodyModel.class),
    REQUEST_CREATED(RequestCreatedEventBodyModel.class),
    REQUEST_DELETED(MatchDeletedEventBodyModel.class),
    RUNTIME_CREATED(RuntimeCreatedEventBodyModel.class),
    RUNTIME_DELETED(RuntimeDeletedEventBodyModel.class),
    RUNTIME_CLIENT_CREATED(RuntimeClientCreatedEventBodyModel.class),
    RUNTIME_CLIENT_DELETED(RuntimeClientDeletedEventBodyModel.class),
    RUNTIME_LOBBY_CREATED(RuntimeLobbyCreatedEventBodyModel.class),
    RUNTIME_LOBBY_DELETED(RuntimeLobbyDeletedEventBodyModel.class),
    RUNTIME_MATCH_CREATED(RuntimeMatchCreatedEventBodyModel.class),
    RUNTIME_MATCH_DELETED(RuntimeMatchDeletedEventBodyModel.class),
    // Player
    CLIENT_MESSAGE_RECEIVED(ClientMessageReceivedEventBodyModel.class),
    MATCHMAKER_MESSAGE_RECEIVED(MatchmakerMessageReceivedEventBodyModel.class),
    // Internal
    INACTIVE_CLIENT_DETECTED(InactiveClientDetectedEventBodyModel.class),
    // Job
    STAGE_JOB_TASK_EXECUTION_REQUESTED(StageJobTaskExecutionRequestedEventBodyModel.class),
    MATCHMAKER_JOB_TASK_EXECUTION_REQUESTED(MatchmakerJobTaskExecutionRequestedEventBodyModel.class),
    MATCH_JOB_TASK_EXECUTION_REQUESTED(MatchJobTaskExecutionRequestedEventBodyModel.class),
    RUNTIME_JOB_TASK_EXECUTION_REQUESTED(RuntimeJobTaskExecutionRequestedEventBodyModel.class);

    final Class<? extends EventBodyModel> bodyClass;

    EventQualifierEnum(Class<? extends EventBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends EventBodyModel> getBodyClass() {
        return bodyClass;
    }
}
