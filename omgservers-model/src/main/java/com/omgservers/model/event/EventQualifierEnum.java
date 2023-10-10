package com.omgservers.model.event;

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
import com.omgservers.model.event.body.MulticastRequestedEventBodyModel;
import com.omgservers.model.event.body.PlayerCreatedEventBodyModel;
import com.omgservers.model.event.body.PlayerDeletedEventBodyModel;
import com.omgservers.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.model.event.body.ProjectDeletedEventBodyModel;
import com.omgservers.model.event.body.ProjectUpdatedEventBodyModel;
import com.omgservers.model.event.body.RequestCreatedEventBodyModel;
import com.omgservers.model.event.body.RequestUpdatedEventBodyModel;
import com.omgservers.model.event.body.RuntimeCreatedEventBodyModel;
import com.omgservers.model.event.body.RuntimeDeletedEventBodyModel;
import com.omgservers.model.event.body.RuntimeUpdatedEventBodyModel;
import com.omgservers.model.event.body.ScriptCreatedEventBodyModel;
import com.omgservers.model.event.body.ScriptDeletedEventBodyModel;
import com.omgservers.model.event.body.ScriptUpdatedEventBodyModel;
import com.omgservers.model.event.body.ServiceAccountCreatedEventBodyModel;
import com.omgservers.model.event.body.ServiceAccountDeletedEventBodyModel;
import com.omgservers.model.event.body.ServiceAccountUpdatedEventBodyModel;
import com.omgservers.model.event.body.SignInRequestedEventBodyModel;
import com.omgservers.model.event.body.SignUpRequestedEventBodyModel;
import com.omgservers.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.model.event.body.StageDeletedEventBodyModel;
import com.omgservers.model.event.body.StageUpdatedEventBodyModel;
import com.omgservers.model.event.body.StopRequestedEventBodyModel;
import com.omgservers.model.event.body.TenantCreatedEventBodyModel;
import com.omgservers.model.event.body.TenantDeletedEventBodyModel;
import com.omgservers.model.event.body.UnicastRequestedEventBodyModel;
import com.omgservers.model.event.body.UserCreatedEventBodyModel;
import com.omgservers.model.event.body.UserDeletedEventBodyModel;
import com.omgservers.model.event.body.UserUpdatedEventBodyModel;
import com.omgservers.model.event.body.VersionCreatedEventBodyModel;
import com.omgservers.model.event.body.VersionDeletedEventBodyModel;

public enum EventQualifierEnum {
    // System events
    INDEX_CREATED(IndexCreatedEventBodyModel.class),
    INDEX_UPDATED(IndexUpdatedEventBodyModel.class),
    INDEX_DELETED(IndexDeletedEventBodyModel.class),
    SERVICE_ACCOUNT_CREATED(ServiceAccountCreatedEventBodyModel.class),
    SERVICE_ACCOUNT_UPDATED(ServiceAccountUpdatedEventBodyModel.class),
    SERVICE_ACCOUNT_DELETED(ServiceAccountDeletedEventBodyModel.class),
    JOB_CREATED(JobCreatedEventBodyModel.class),
    JOB_UPDATED(JobUpdatedEventBodyModel.class),
    JOB_DELETED(JobDeletedEventBodyModel.class),
    // Entity events
    TENANT_CREATED(TenantCreatedEventBodyModel.class),
    TENANT_UPDATED(TenantCreatedEventBodyModel.class),
    TENANT_DELETED(TenantDeletedEventBodyModel.class),
    PROJECT_CREATED(ProjectCreatedEventBodyModel.class),
    PROJECT_UPDATED(ProjectUpdatedEventBodyModel.class),
    PROJECT_DELETED(ProjectDeletedEventBodyModel.class),
    STAGE_CREATED(StageCreatedEventBodyModel.class),
    STAGE_UPDATED(StageUpdatedEventBodyModel.class),
    STAGE_DELETED(StageDeletedEventBodyModel.class),
    VERSION_CREATED(VersionCreatedEventBodyModel.class),
    VERSION_DELETED(VersionDeletedEventBodyModel.class),
    USER_CREATED(UserCreatedEventBodyModel.class),
    USER_UPDATED(UserUpdatedEventBodyModel.class),
    USER_DELETED(UserDeletedEventBodyModel.class),
    PLAYER_CREATED(PlayerCreatedEventBodyModel.class),
    PLAYER_UPDATED(PlayerCreatedEventBodyModel.class),
    PLAYER_DELETED(PlayerDeletedEventBodyModel.class),
    CLIENT_CREATED(ClientCreatedEventBodyModel.class),
    CLIENT_UPDATED(ClientUpdatedEventBodyModel.class),
    CLIENT_DELETED(ClientDeletedEventBodyModel.class),
    CLIENT_DISCONNECTED(ClientDisconnectedEventBodyModel.class),
    MATCHMAKER_CREATED(MatchmakerCreatedEventBodyModel.class),
    MATCHMAKER_UPDATED(MatchmakerUpdatedEventBodyModel.class),
    MATCHMAKER_DELETED(MatchmakerDeletedEventBodyModel.class),
    MATCH_CREATED(MatchCreatedEventBodyModel.class),
    MATCH_UPDATED(MatchUpdatedEventBodyModel.class),
    MATCH_DELETED(MatchDeletedEventBodyModel.class),
    MATCH_CLIENT_CREATED(MatchClientCreatedEventBodyModel.class),
    MATCH_CLIENT_UPDATED(MatchClientUpdatedEventBodyModel.class),
    MATCH_CLIENT_DELETED(MatchClientDeletedEventBodyModel.class),
    REQUEST_CREATED(RequestCreatedEventBodyModel.class),
    REQUEST_UPDATED(RequestUpdatedEventBodyModel.class),
    REQUEST_DELETED(MatchDeletedEventBodyModel.class),
    RUNTIME_CREATED(RuntimeCreatedEventBodyModel.class),
    RUNTIME_UPDATED(RuntimeUpdatedEventBodyModel.class),
    RUNTIME_DELETED(RuntimeDeletedEventBodyModel.class),
    SCRIPT_CREATED(ScriptCreatedEventBodyModel.class),
    SCRIPT_UPDATED(ScriptUpdatedEventBodyModel.class),
    SCRIPT_DELETED(ScriptDeletedEventBodyModel.class),
    // Incoming events
    SIGN_UP_REQUESTED(SignUpRequestedEventBodyModel.class),
    SIGN_IN_REQUESTED(SignInRequestedEventBodyModel.class),
    MATCHMAKER_REQUESTED(MatchmakerRequestedEventBodyModel.class),
    MATCH_REQUESTED(MatchRequestedEventBodyModel.class),
    CHANGE_REQUESTED(ChangeRequestedEventBodyModel.class),
    // Runtime events
    UNICAST_REQUESTED(UnicastRequestedEventBodyModel.class),
    MULTICAST_REQUESTED(MulticastRequestedEventBodyModel.class),
    BROADCAST_REQUESTED(BroadcastRequestedEventBodyModel.class),
    KICK_REQUESTED(KickRequestedEventBodyModel.class),
    STOP_REQUESTED(StopRequestedEventBodyModel.class);

    Class<? extends EventBodyModel> bodyClass;

    EventQualifierEnum(Class<? extends EventBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends EventBodyModel> getBodyClass() {
        return bodyClass;
    }
}
