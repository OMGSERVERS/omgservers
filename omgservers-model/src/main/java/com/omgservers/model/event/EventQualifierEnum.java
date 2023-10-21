package com.omgservers.model.event;

import com.omgservers.model.event.body.BroadcastApprovedEventBodyModel;
import com.omgservers.model.event.body.BroadcastRequestedEventBodyModel;
import com.omgservers.model.event.body.ChangeRequestedEventBodyModel;
import com.omgservers.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.model.event.body.ClientDeletedEventBodyModel;
import com.omgservers.model.event.body.ClientDisconnectedEventBodyModel;
import com.omgservers.model.event.body.ClientUpdatedEventBodyModel;
import com.omgservers.model.event.body.IndexCreatedEventBodyModel;
import com.omgservers.model.event.body.IndexDeletedEventBodyModel;
import com.omgservers.model.event.body.IndexUpdatedEventBodyModel;
import com.omgservers.model.event.body.JobCreatedEventBodyModel;
import com.omgservers.model.event.body.JobDeletedEventBodyModel;
import com.omgservers.model.event.body.JobUpdatedEventBodyModel;
import com.omgservers.model.event.body.KickApprovedEventBodyModel;
import com.omgservers.model.event.body.KickRequestedEventBodyModel;
import com.omgservers.model.event.body.MatchClientCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchClientDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchClientUpdatedEventBodyModel;
import com.omgservers.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchRequestedEventBodyModel;
import com.omgservers.model.event.body.MatchUpdatedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerRequestedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerUpdatedEventBodyModel;
import com.omgservers.model.event.body.MulticastApprovedEventBodyModel;
import com.omgservers.model.event.body.MulticastRequestedEventBodyModel;
import com.omgservers.model.event.body.PlayerCreatedEventBodyModel;
import com.omgservers.model.event.body.PlayerDeletedEventBodyModel;
import com.omgservers.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.model.event.body.ProjectDeletedEventBodyModel;
import com.omgservers.model.event.body.ProjectUpdatedEventBodyModel;
import com.omgservers.model.event.body.RequestCreatedEventBodyModel;
import com.omgservers.model.event.body.RequestUpdatedEventBodyModel;
import com.omgservers.model.event.body.RespondApprovedEventBodyModel;
import com.omgservers.model.event.body.RespondRequestedEventBodyModel;
import com.omgservers.model.event.body.RuntimeCreatedEventBodyModel;
import com.omgservers.model.event.body.RuntimeDeletedEventBodyModel;
import com.omgservers.model.event.body.RuntimeUpdatedEventBodyModel;
import com.omgservers.model.event.body.ScriptCreatedEventBodyModel;
import com.omgservers.model.event.body.ScriptDeletedEventBodyModel;
import com.omgservers.model.event.body.ScriptUpdatedEventBodyModel;
import com.omgservers.model.event.body.ServiceAccountCreatedEventBodyModel;
import com.omgservers.model.event.body.ServiceAccountDeletedEventBodyModel;
import com.omgservers.model.event.body.ServiceAccountUpdatedEventBodyModel;
import com.omgservers.model.event.body.SetAttributesApprovedEventBodyModel;
import com.omgservers.model.event.body.SetAttributesRequestedEventBodyModel;
import com.omgservers.model.event.body.SetObjectApprovedEventBodyModel;
import com.omgservers.model.event.body.SetObjectRequestedEventBodyModel;
import com.omgservers.model.event.body.SignInRequestedEventBodyModel;
import com.omgservers.model.event.body.SignUpRequestedEventBodyModel;
import com.omgservers.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.model.event.body.StageDeletedEventBodyModel;
import com.omgservers.model.event.body.StageUpdatedEventBodyModel;
import com.omgservers.model.event.body.StopApprovedEventBodyModel;
import com.omgservers.model.event.body.StopRequestedEventBodyModel;
import com.omgservers.model.event.body.TenantCreatedEventBodyModel;
import com.omgservers.model.event.body.TenantDeletedEventBodyModel;
import com.omgservers.model.event.body.UnicastApprovedEventBodyModel;
import com.omgservers.model.event.body.UnicastRequestedEventBodyModel;
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
    // Entity
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
    SCRIPT_CREATED(ScriptCreatedEventBodyModel.class, true),
    SCRIPT_UPDATED(ScriptUpdatedEventBodyModel.class, true),
    SCRIPT_DELETED(ScriptDeletedEventBodyModel.class, true),
    // Gateway
    SIGN_UP_REQUESTED(SignUpRequestedEventBodyModel.class, true),
    SIGN_IN_REQUESTED(SignInRequestedEventBodyModel.class, true),
    MATCHMAKER_REQUESTED(MatchmakerRequestedEventBodyModel.class, true),
    MATCH_REQUESTED(MatchRequestedEventBodyModel.class, true),
    CHANGE_REQUESTED(ChangeRequestedEventBodyModel.class, false),
    // Runtime
    RESPOND_REQUESTED(RespondRequestedEventBodyModel.class, false),
    RESPOND_APPROVED(RespondApprovedEventBodyModel.class, false),
    SET_ATTRIBUTES_REQUESTED(SetAttributesRequestedEventBodyModel.class, false),
    SET_ATTRIBUTES_APPROVED(SetAttributesApprovedEventBodyModel.class, false),
    SET_OBJECT_REQUESTED(SetObjectRequestedEventBodyModel.class, false),
    SET_OBJECT_APPROVED(SetObjectApprovedEventBodyModel.class, false),
    UNICAST_REQUESTED(UnicastRequestedEventBodyModel.class, false),
    UNICAST_APPROVED(UnicastApprovedEventBodyModel.class, false),
    MULTICAST_REQUESTED(MulticastRequestedEventBodyModel.class, false),
    MULTICAST_APPROVED(MulticastApprovedEventBodyModel.class, false),
    BROADCAST_REQUESTED(BroadcastRequestedEventBodyModel.class, false),
    BROADCAST_APPROVED(BroadcastApprovedEventBodyModel.class, false),
    KICK_REQUESTED(KickRequestedEventBodyModel.class, true),
    KICK_APPROVED(KickApprovedEventBodyModel.class, true),
    STOP_REQUESTED(StopRequestedEventBodyModel.class, true),
    STOP_APPROVED(StopApprovedEventBodyModel.class, true);

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
