package com.omgservers.model.event;

import com.omgservers.model.event.body.internal.InactiveClientDetectedEventBodyModel;
import com.omgservers.model.event.body.internal.InactiveRuntimeDetectedEventBodyModel;
import com.omgservers.model.event.body.internal.LobbyAssignmentRequestedEventBodyModel;
import com.omgservers.model.event.body.internal.MatchmakerAssignmentRequestedEventBodyModel;
import com.omgservers.model.event.body.job.MatchmakerJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.model.event.body.job.RuntimeJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.model.event.body.job.StageJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.model.event.body.module.ClientCreatedEventBodyModel;
import com.omgservers.model.event.body.module.ClientDeletedEventBodyModel;
import com.omgservers.model.event.body.module.ClientMatchmakerRefCreatedEventBodyModel;
import com.omgservers.model.event.body.module.ClientMatchmakerRefDeletedEventBodyModel;
import com.omgservers.model.event.body.module.ClientRuntimeRefCreatedEventBodyModel;
import com.omgservers.model.event.body.module.ClientRuntimeRefDeletedEventBodyModel;
import com.omgservers.model.event.body.module.LobbyCreatedEventBodyModel;
import com.omgservers.model.event.body.module.LobbyDeletedEventBodyModel;
import com.omgservers.model.event.body.module.LobbyRuntimeRefCreatedEventBodyModel;
import com.omgservers.model.event.body.module.LobbyRuntimeRefDeletedEventBodyModel;
import com.omgservers.model.event.body.module.MatchmakerAssignmentCreatedEventBodyModel;
import com.omgservers.model.event.body.module.MatchmakerAssignmentDeletedEventBodyModel;
import com.omgservers.model.event.body.module.MatchmakerCreatedEventBodyModel;
import com.omgservers.model.event.body.module.MatchmakerDeletedEventBodyModel;
import com.omgservers.model.event.body.module.MatchmakerMatchClientCreatedEventBodyModel;
import com.omgservers.model.event.body.module.MatchmakerMatchClientDeletedEventBodyModel;
import com.omgservers.model.event.body.module.MatchmakerMatchCreatedEventBodyModel;
import com.omgservers.model.event.body.module.MatchmakerMatchDeletedEventBodyModel;
import com.omgservers.model.event.body.module.MatchmakerMatchRuntimeRefCreatedEventBodyModel;
import com.omgservers.model.event.body.module.MatchmakerMatchRuntimeRefDeletedEventBodyModel;
import com.omgservers.model.event.body.module.MatchmakerRequestCreatedEventBodyModel;
import com.omgservers.model.event.body.module.MatchmakerRequestDeletedEventBodyModel;
import com.omgservers.model.event.body.module.PlayerCreatedEventBodyModel;
import com.omgservers.model.event.body.module.PlayerDeletedEventBodyModel;
import com.omgservers.model.event.body.module.ProjectCreatedEventBodyModel;
import com.omgservers.model.event.body.module.ProjectDeletedEventBodyModel;
import com.omgservers.model.event.body.module.RuntimeClientCreatedEventBodyModel;
import com.omgservers.model.event.body.module.RuntimeClientDeletedEventBodyModel;
import com.omgservers.model.event.body.module.RuntimeCreatedEventBodyModel;
import com.omgservers.model.event.body.module.RuntimeDeletedEventBodyModel;
import com.omgservers.model.event.body.module.RuntimeLobbyCreatedEventBodyModel;
import com.omgservers.model.event.body.module.RuntimeLobbyDeletedEventBodyModel;
import com.omgservers.model.event.body.module.RuntimeMatchCreatedEventBodyModel;
import com.omgservers.model.event.body.module.RuntimeMatchDeletedEventBodyModel;
import com.omgservers.model.event.body.module.StageCreatedEventBodyModel;
import com.omgservers.model.event.body.module.StageDeletedEventBodyModel;
import com.omgservers.model.event.body.module.TenantCreatedEventBodyModel;
import com.omgservers.model.event.body.module.TenantDeletedEventBodyModel;
import com.omgservers.model.event.body.module.UserCreatedEventBodyModel;
import com.omgservers.model.event.body.module.UserDeletedEventBodyModel;
import com.omgservers.model.event.body.module.VersionCreatedEventBodyModel;
import com.omgservers.model.event.body.module.VersionDeletedEventBodyModel;
import com.omgservers.model.event.body.module.VersionLobbyRefCreatedEventBodyModel;
import com.omgservers.model.event.body.module.VersionLobbyRefDeletedEventBodyModel;
import com.omgservers.model.event.body.module.VersionLobbyRequestCreatedEventBodyModel;
import com.omgservers.model.event.body.module.VersionLobbyRequestDeletedEventBodyModel;
import com.omgservers.model.event.body.module.VersionMatchmakerRefCreatedEventBodyModel;
import com.omgservers.model.event.body.module.VersionMatchmakerRefDeletedEventBodyModel;
import com.omgservers.model.event.body.module.VersionMatchmakerRequestCreatedEventBodyModel;
import com.omgservers.model.event.body.module.VersionMatchmakerRequestDeletedEventBodyModel;
import com.omgservers.model.event.body.player.ClientMessageReceivedEventBodyModel;
import com.omgservers.model.event.body.player.MatchmakerMessageReceivedEventBodyModel;
import com.omgservers.model.event.body.system.ContainerCreatedEventBodyModel;
import com.omgservers.model.event.body.system.ContainerDeletedEventBodyModel;
import com.omgservers.model.event.body.system.IndexCreatedEventBodyModel;
import com.omgservers.model.event.body.system.IndexDeletedEventBodyModel;
import com.omgservers.model.event.body.system.ServiceAccountCreatedEventBodyModel;
import com.omgservers.model.event.body.system.ServiceAccountDeletedEventBodyModel;

public enum EventQualifierEnum {
    // System
    INDEX_CREATED(IndexCreatedEventBodyModel.class),
    INDEX_DELETED(IndexDeletedEventBodyModel.class),
    SERVICE_ACCOUNT_CREATED(ServiceAccountCreatedEventBodyModel.class),
    SERVICE_ACCOUNT_DELETED(ServiceAccountDeletedEventBodyModel.class),
    CONTAINER_CREATED(ContainerCreatedEventBodyModel.class),
    CONTAINER_DELETED(ContainerDeletedEventBodyModel.class),
    // Module
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
    CLIENT_MATCHMAKER_REF_CREATED(ClientMatchmakerRefCreatedEventBodyModel.class),
    CLIENT_MATCHMAKER_REF_DELETED(ClientMatchmakerRefDeletedEventBodyModel.class),
    LOBBY_CREATED(LobbyCreatedEventBodyModel.class),
    LOBBY_DELETED(LobbyDeletedEventBodyModel.class),
    LOBBY_RUNTIME_REF_CREATED(LobbyRuntimeRefCreatedEventBodyModel.class),
    LOBBY_RUNTIME_REF_DELETED(LobbyRuntimeRefDeletedEventBodyModel.class),
    MATCHMAKER_CREATED(MatchmakerCreatedEventBodyModel.class),
    MATCHMAKER_DELETED(MatchmakerDeletedEventBodyModel.class),
    MATCHMAKER_MATCH_CREATED(MatchmakerMatchCreatedEventBodyModel.class),
    MATCHMAKER_MATCH_DELETED(MatchmakerMatchDeletedEventBodyModel.class),
    MATCHMAKER_MATCH_CLIENT_CREATED(MatchmakerMatchClientCreatedEventBodyModel.class),
    MATCHMAKER_MATCH_CLIENT_DELETED(MatchmakerMatchClientDeletedEventBodyModel.class),
    MATCHMAKER_MATCH_RUNTIME_REF_CREATED(MatchmakerMatchRuntimeRefCreatedEventBodyModel.class),
    MATCHMAKER_MATCH_RUNTIME_REF_DELETED(MatchmakerMatchRuntimeRefDeletedEventBodyModel.class),
    MATCHMAKER_REQUEST_CREATED(MatchmakerRequestCreatedEventBodyModel.class),
    MATCHMAKER_REQUEST_DELETED(MatchmakerRequestDeletedEventBodyModel.class),
    MATCHMAKER_ASSIGNMENT_CREATED(MatchmakerAssignmentCreatedEventBodyModel.class),
    MATCHMAKER_ASSIGNMENT_DELETED(MatchmakerAssignmentDeletedEventBodyModel.class),
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
    INACTIVE_RUNTIME_DETECTED(InactiveRuntimeDetectedEventBodyModel.class),
    LOBBY_ASSIGNMENT_REQUESTED(LobbyAssignmentRequestedEventBodyModel.class),
    MATCHMAKER_ASSIGNMENT_REQUESTED(MatchmakerAssignmentRequestedEventBodyModel.class),
    // Job
    STAGE_JOB_TASK_EXECUTION_REQUESTED(StageJobTaskExecutionRequestedEventBodyModel.class),
    MATCHMAKER_JOB_TASK_EXECUTION_REQUESTED(MatchmakerJobTaskExecutionRequestedEventBodyModel.class),
    RUNTIME_JOB_TASK_EXECUTION_REQUESTED(RuntimeJobTaskExecutionRequestedEventBodyModel.class);

    final Class<? extends EventBodyModel> bodyClass;

    EventQualifierEnum(Class<? extends EventBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends EventBodyModel> getBodyClass() {
        return bodyClass;
    }
}
