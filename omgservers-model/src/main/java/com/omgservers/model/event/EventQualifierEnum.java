package com.omgservers.model.event;

import com.omgservers.model.event.body.BroadcastCommandApprovedEventBodyModel;
import com.omgservers.model.event.body.BroadcastCommandReceivedEventBodyModel;
import com.omgservers.model.event.body.ChangeCommandApprovedEventBodyModel;
import com.omgservers.model.event.body.ChangeCommandReceivedEventBodyModel;
import com.omgservers.model.event.body.ChangeMessageReceivedEventBodyModel;
import com.omgservers.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.model.event.body.ClientDeletedEventBodyModel;
import com.omgservers.model.event.body.ClientDisconnectedEventBodyModel;
import com.omgservers.model.event.body.ClientUpdatedEventBodyModel;
import com.omgservers.model.event.body.ContainerCreatedEventBodyModel;
import com.omgservers.model.event.body.ContainerDeletedEventBodyModel;
import com.omgservers.model.event.body.ContainerUpdatedEventBodyModel;
import com.omgservers.model.event.body.IndexCreatedEventBodyModel;
import com.omgservers.model.event.body.IndexDeletedEventBodyModel;
import com.omgservers.model.event.body.IndexUpdatedEventBodyModel;
import com.omgservers.model.event.body.JobCreatedEventBodyModel;
import com.omgservers.model.event.body.JobDeletedEventBodyModel;
import com.omgservers.model.event.body.JobUpdatedEventBodyModel;
import com.omgservers.model.event.body.KickCommandApprovedEventBodyModel;
import com.omgservers.model.event.body.KickCommandReceivedEventBodyModel;
import com.omgservers.model.event.body.MatchClientCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchClientDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchClientUpdatedEventBodyModel;
import com.omgservers.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchMessageReceivedEventBodyModel;
import com.omgservers.model.event.body.MatchUpdatedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerMessageReceivedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerUpdatedEventBodyModel;
import com.omgservers.model.event.body.MulticastCommandApprovedEventBodyModel;
import com.omgservers.model.event.body.MulticastCommandReceivedEventBodyModel;
import com.omgservers.model.event.body.PlayerCreatedEventBodyModel;
import com.omgservers.model.event.body.PlayerDeletedEventBodyModel;
import com.omgservers.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.model.event.body.ProjectDeletedEventBodyModel;
import com.omgservers.model.event.body.ProjectUpdatedEventBodyModel;
import com.omgservers.model.event.body.RequestCreatedEventBodyModel;
import com.omgservers.model.event.body.RequestUpdatedEventBodyModel;
import com.omgservers.model.event.body.RespondCommandApprovedEventBodyModel;
import com.omgservers.model.event.body.RespondCommandReceivedEventBodyModel;
import com.omgservers.model.event.body.RuntimeCreatedEventBodyModel;
import com.omgservers.model.event.body.RuntimeDeletedEventBodyModel;
import com.omgservers.model.event.body.RuntimeUpdatedEventBodyModel;
import com.omgservers.model.event.body.ServiceAccountCreatedEventBodyModel;
import com.omgservers.model.event.body.ServiceAccountDeletedEventBodyModel;
import com.omgservers.model.event.body.ServiceAccountUpdatedEventBodyModel;
import com.omgservers.model.event.body.SetAttributesCommandApprovedEventBodyModel;
import com.omgservers.model.event.body.SetAttributesCommandReceivedEventBodyModel;
import com.omgservers.model.event.body.SetObjectCommandApprovedEventBodyModel;
import com.omgservers.model.event.body.SetObjectCommandReceivedEventBodyModel;
import com.omgservers.model.event.body.SignInMessageReceivedEventBodyModel;
import com.omgservers.model.event.body.SignUpMessageReceivedEventBodyModel;
import com.omgservers.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.model.event.body.StageDeletedEventBodyModel;
import com.omgservers.model.event.body.StageUpdatedEventBodyModel;
import com.omgservers.model.event.body.StopCommandApprovedEventBodyModel;
import com.omgservers.model.event.body.StopCommandReceivedEventBodyModel;
import com.omgservers.model.event.body.TenantCreatedEventBodyModel;
import com.omgservers.model.event.body.TenantDeletedEventBodyModel;
import com.omgservers.model.event.body.UnicastCommandApprovedEventBodyModel;
import com.omgservers.model.event.body.UnicastCommandReceivedEventBodyModel;
import com.omgservers.model.event.body.UserCreatedEventBodyModel;
import com.omgservers.model.event.body.UserDeletedEventBodyModel;
import com.omgservers.model.event.body.UserUpdatedEventBodyModel;
import com.omgservers.model.event.body.VersionCreatedEventBodyModel;
import com.omgservers.model.event.body.VersionDeletedEventBodyModel;
import com.omgservers.model.event.body.VersionMatchmakerCreatedEventBodyModel;
import com.omgservers.model.event.body.VersionMatchmakerDeletedEventBodyModel;
import com.omgservers.model.event.body.VersionMatchmakerUpdatedEventBodyModel;
import com.omgservers.model.event.body.VersionRuntimeCreatedEventBodyModel;
import com.omgservers.model.event.body.VersionRuntimeDeletedEventBodyModel;
import com.omgservers.model.event.body.VersionRuntimeUpdatedEventBodyModel;
import com.omgservers.model.event.body.VersionUpdatedEventBodyModel;

public enum EventQualifierEnum {
    // System
    INDEX_CREATED(IndexCreatedEventBodyModel.class, true),
    INDEX_UPDATED(IndexUpdatedEventBodyModel.class, true),
    INDEX_DELETED(IndexDeletedEventBodyModel.class, true),
    SERVICE_ACCOUNT_CREATED(ServiceAccountCreatedEventBodyModel.class, true),
    SERVICE_ACCOUNT_UPDATED(ServiceAccountUpdatedEventBodyModel.class, true),
    SERVICE_ACCOUNT_DELETED(ServiceAccountDeletedEventBodyModel.class, true),
    JOB_CREATED(JobCreatedEventBodyModel.class, true),
    JOB_UPDATED(JobUpdatedEventBodyModel.class, true),
    JOB_DELETED(JobDeletedEventBodyModel.class, true),
    CONTAINER_CREATED(ContainerCreatedEventBodyModel.class, true),
    CONTAINER_UPDATED(ContainerUpdatedEventBodyModel.class, true),
    CONTAINER_DELETED(ContainerDeletedEventBodyModel.class, true),
    // Application
    TENANT_CREATED(TenantCreatedEventBodyModel.class, true),
    TENANT_UPDATED(TenantCreatedEventBodyModel.class, true),
    TENANT_DELETED(TenantDeletedEventBodyModel.class, true),
    PROJECT_CREATED(ProjectCreatedEventBodyModel.class, true),
    PROJECT_UPDATED(ProjectUpdatedEventBodyModel.class, true),
    PROJECT_DELETED(ProjectDeletedEventBodyModel.class, true),
    STAGE_CREATED(StageCreatedEventBodyModel.class, true),
    STAGE_UPDATED(StageUpdatedEventBodyModel.class, true),
    STAGE_DELETED(StageDeletedEventBodyModel.class, true),
    VERSION_MATCHMAKER_CREATED(VersionMatchmakerCreatedEventBodyModel.class, true),
    VERSION_MATCHMAKER_UPDATED(VersionMatchmakerUpdatedEventBodyModel.class, true),
    VERSION_MATCHMAKER_DELETED(VersionMatchmakerDeletedEventBodyModel.class, true),
    VERSION_RUNTIME_CREATED(VersionRuntimeCreatedEventBodyModel.class, true),
    VERSION_RUNTIME_UPDATED(VersionRuntimeUpdatedEventBodyModel.class, true),
    VERSION_RUNTIME_DELETED(VersionRuntimeDeletedEventBodyModel.class, true),
    VERSION_CREATED(VersionCreatedEventBodyModel.class, true),
    VERSION_UPDATED(VersionUpdatedEventBodyModel.class, true),
    VERSION_DELETED(VersionDeletedEventBodyModel.class, true),
    USER_CREATED(UserCreatedEventBodyModel.class, true),
    USER_UPDATED(UserUpdatedEventBodyModel.class, true),
    USER_DELETED(UserDeletedEventBodyModel.class, true),
    PLAYER_CREATED(PlayerCreatedEventBodyModel.class, true),
    PLAYER_UPDATED(PlayerCreatedEventBodyModel.class, true),
    PLAYER_DELETED(PlayerDeletedEventBodyModel.class, true),
    CLIENT_CREATED(ClientCreatedEventBodyModel.class, true),
    CLIENT_UPDATED(ClientUpdatedEventBodyModel.class, true),
    CLIENT_DELETED(ClientDeletedEventBodyModel.class, true),
    CLIENT_DISCONNECTED(ClientDisconnectedEventBodyModel.class, true),
    MATCHMAKER_CREATED(MatchmakerCreatedEventBodyModel.class, true),
    MATCHMAKER_UPDATED(MatchmakerUpdatedEventBodyModel.class, true),
    MATCHMAKER_DELETED(MatchmakerDeletedEventBodyModel.class, true),
    MATCH_CREATED(MatchCreatedEventBodyModel.class, true),
    MATCH_UPDATED(MatchUpdatedEventBodyModel.class, true),
    MATCH_DELETED(MatchDeletedEventBodyModel.class, true),
    MATCH_CLIENT_CREATED(MatchClientCreatedEventBodyModel.class, true),
    MATCH_CLIENT_UPDATED(MatchClientUpdatedEventBodyModel.class, true),
    MATCH_CLIENT_DELETED(MatchClientDeletedEventBodyModel.class, true),
    REQUEST_CREATED(RequestCreatedEventBodyModel.class, true),
    REQUEST_UPDATED(RequestUpdatedEventBodyModel.class, true),
    REQUEST_DELETED(MatchDeletedEventBodyModel.class, true),
    RUNTIME_CREATED(RuntimeCreatedEventBodyModel.class, true),
    RUNTIME_UPDATED(RuntimeUpdatedEventBodyModel.class, true),
    RUNTIME_DELETED(RuntimeDeletedEventBodyModel.class, true),
    // Gateway
    SIGN_UP_MESSAGE_RECEIVED(SignUpMessageReceivedEventBodyModel.class, true),
    SIGN_IN_MESSAGE_RECEIVED(SignInMessageReceivedEventBodyModel.class, true),
    MATCHMAKER_MESSAGE_RECEIVED(MatchmakerMessageReceivedEventBodyModel.class, true),
    MATCH_MESSAGE_RECEIVED(MatchMessageReceivedEventBodyModel.class, true),
    CHANGE_MESSAGE_RECEIVED(ChangeMessageReceivedEventBodyModel.class, false),
    // Commands
    RESPOND_COMMAND_RECEIVED(RespondCommandReceivedEventBodyModel.class, false),
    RESPOND_COMMAND_APPROVED(RespondCommandApprovedEventBodyModel.class, false),
    SET_ATTRIBUTES_COMMAND_RECEIVED(SetAttributesCommandReceivedEventBodyModel.class, false),
    SET_ATTRIBUTES_COMMAND_APPROVED(SetAttributesCommandApprovedEventBodyModel.class, false),
    SET_OBJECT_COMMAND_RECEIVED(SetObjectCommandReceivedEventBodyModel.class, false),
    SET_OBJECT_COMMAND_APPROVED(SetObjectCommandApprovedEventBodyModel.class, false),
    UNICAST_COMMAND_RECEIVED(UnicastCommandReceivedEventBodyModel.class, false),
    UNICAST_COMMAND_APPROVED(UnicastCommandApprovedEventBodyModel.class, false),
    MULTICAST_COMMAND_RECEIVED(MulticastCommandReceivedEventBodyModel.class, false),
    MULTICAST_COMMAND_APPROVED(MulticastCommandApprovedEventBodyModel.class, false),
    BROADCAST_COMMAND_RECEIVED(BroadcastCommandReceivedEventBodyModel.class, false),
    BROADCAST_COMMAND_APPROVED(BroadcastCommandApprovedEventBodyModel.class, false),
    CHANGE_COMMAND_RECEIVED(ChangeCommandReceivedEventBodyModel.class, false),
    CHANGE_COMMAND_APPROVED(ChangeCommandApprovedEventBodyModel.class, false),
    KICK_COMMAND_RECEIVED(KickCommandReceivedEventBodyModel.class, true),
    KICK_COMMAND_APPROVED(KickCommandApprovedEventBodyModel.class, true),
    STOP_COMMAND_RECEIVED(StopCommandReceivedEventBodyModel.class, true),
    STOP_COMMAND_APPROVED(StopCommandApprovedEventBodyModel.class, true);

    final Class<? extends EventBodyModel> bodyClass;
    final Boolean infoLogging;

    EventQualifierEnum(Class<? extends EventBodyModel> bodyClass, Boolean infoLogging) {
        this.bodyClass = bodyClass;
        this.infoLogging = infoLogging;
    }

    public Class<? extends EventBodyModel> getBodyClass() {
        return bodyClass;
    }

    public Boolean getInfoLogging() {
        return infoLogging;
    }
}
