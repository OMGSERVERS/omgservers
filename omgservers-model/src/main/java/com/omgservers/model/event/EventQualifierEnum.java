package com.omgservers.model.event;

import com.omgservers.model.event.body.internal.InactiveClientDetectedEventBodyModel;
import com.omgservers.model.event.body.internal.InactiveRuntimeDetectedEventBodyModel;
import com.omgservers.model.event.body.internal.LobbyAssignmentRequestedEventBodyModel;
import com.omgservers.model.event.body.internal.MatchmakerAssignmentRequestedEventBodyModel;
import com.omgservers.model.event.body.job.MatchmakerJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.model.event.body.job.RuntimeJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.model.event.body.job.StageJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.model.event.body.module.tenant.StageCreatedEventBodyModel;
import com.omgservers.model.event.body.module.tenant.StageDeletedEventBodyModel;
import com.omgservers.model.event.body.module.tenant.TenantCreatedEventBodyModel;
import com.omgservers.model.event.body.module.tenant.TenantDeletedEventBodyModel;
import com.omgservers.model.event.body.module.tenant.VersionCreatedEventBodyModel;
import com.omgservers.model.event.body.module.tenant.VersionDeletedEventBodyModel;
import com.omgservers.model.event.body.module.tenant.VersionLobbyRefCreatedEventBodyModel;
import com.omgservers.model.event.body.module.tenant.VersionLobbyRefDeletedEventBodyModel;
import com.omgservers.model.event.body.module.tenant.VersionLobbyRequestCreatedEventBodyModel;
import com.omgservers.model.event.body.module.tenant.VersionLobbyRequestDeletedEventBodyModel;
import com.omgservers.model.event.body.module.tenant.VersionMatchmakerRefCreatedEventBodyModel;
import com.omgservers.model.event.body.module.tenant.VersionMatchmakerRefDeletedEventBodyModel;
import com.omgservers.model.event.body.module.tenant.VersionMatchmakerRequestCreatedEventBodyModel;
import com.omgservers.model.event.body.module.tenant.VersionMatchmakerRequestDeletedEventBodyModel;
import com.omgservers.model.event.body.module.client.ClientCreatedEventBodyModel;
import com.omgservers.model.event.body.module.client.ClientDeletedEventBodyModel;
import com.omgservers.model.event.body.module.client.ClientMatchmakerRefCreatedEventBodyModel;
import com.omgservers.model.event.body.module.client.ClientMatchmakerRefDeletedEventBodyModel;
import com.omgservers.model.event.body.module.client.ClientRuntimeRefCreatedEventBodyModel;
import com.omgservers.model.event.body.module.client.ClientRuntimeRefDeletedEventBodyModel;
import com.omgservers.model.event.body.module.lobby.LobbyCreatedEventBodyModel;
import com.omgservers.model.event.body.module.lobby.LobbyDeletedEventBodyModel;
import com.omgservers.model.event.body.module.lobby.LobbyRuntimeRefCreatedEventBodyModel;
import com.omgservers.model.event.body.module.lobby.LobbyRuntimeRefDeletedEventBodyModel;
import com.omgservers.model.event.body.module.matchmaker.MatchmakerAssignmentCreatedEventBodyModel;
import com.omgservers.model.event.body.module.matchmaker.MatchmakerAssignmentDeletedEventBodyModel;
import com.omgservers.model.event.body.module.matchmaker.MatchmakerCreatedEventBodyModel;
import com.omgservers.model.event.body.module.matchmaker.MatchmakerDeletedEventBodyModel;
import com.omgservers.model.event.body.module.matchmaker.MatchmakerMatchClientCreatedEventBodyModel;
import com.omgservers.model.event.body.module.matchmaker.MatchmakerMatchClientDeletedEventBodyModel;
import com.omgservers.model.event.body.module.matchmaker.MatchmakerMatchCreatedEventBodyModel;
import com.omgservers.model.event.body.module.matchmaker.MatchmakerMatchDeletedEventBodyModel;
import com.omgservers.model.event.body.module.matchmaker.MatchmakerMatchRuntimeRefCreatedEventBodyModel;
import com.omgservers.model.event.body.module.matchmaker.MatchmakerMatchRuntimeRefDeletedEventBodyModel;
import com.omgservers.model.event.body.module.matchmaker.MatchmakerRequestCreatedEventBodyModel;
import com.omgservers.model.event.body.module.matchmaker.MatchmakerRequestDeletedEventBodyModel;
import com.omgservers.model.event.body.module.user.PlayerCreatedEventBodyModel;
import com.omgservers.model.event.body.module.user.PlayerDeletedEventBodyModel;
import com.omgservers.model.event.body.module.tenant.ProjectCreatedEventBodyModel;
import com.omgservers.model.event.body.module.tenant.ProjectDeletedEventBodyModel;
import com.omgservers.model.event.body.module.runtime.RuntimeClientCreatedEventBodyModel;
import com.omgservers.model.event.body.module.runtime.RuntimeClientDeletedEventBodyModel;
import com.omgservers.model.event.body.module.runtime.RuntimeCreatedEventBodyModel;
import com.omgservers.model.event.body.module.runtime.RuntimeDeletedEventBodyModel;
import com.omgservers.model.event.body.module.user.UserCreatedEventBodyModel;
import com.omgservers.model.event.body.module.user.UserDeletedEventBodyModel;
import com.omgservers.model.event.body.internal.ClientMessageReceivedEventBodyModel;
import com.omgservers.model.event.body.internal.MatchmakerMessageReceivedEventBodyModel;
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
    // Internal
    CLIENT_MESSAGE_RECEIVED(ClientMessageReceivedEventBodyModel.class),
    MATCHMAKER_MESSAGE_RECEIVED(MatchmakerMessageReceivedEventBodyModel.class),
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
