package com.omgservers.model.event;

import com.omgservers.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.model.event.body.ClientDisconnectedEventBodyModel;
import com.omgservers.model.event.body.ClientUpdatedEventBodyModel;
import com.omgservers.model.event.body.JobCreatedEventBodyModel;
import com.omgservers.model.event.body.JobDeletedEventBodyModel;
import com.omgservers.model.event.body.JobUpdatedEventBodyModel;
import com.omgservers.model.event.body.MatchClientCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchClientDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchUpdatedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerRequestedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerUpdatedEventBodyModel;
import com.omgservers.model.event.body.PlayerCreatedEventBodyModel;
import com.omgservers.model.event.body.PlayerSignedInEventBodyModel;
import com.omgservers.model.event.body.PlayerSignedUpEventBodyModel;
import com.omgservers.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.model.event.body.ProjectDeletedEventBodyModel;
import com.omgservers.model.event.body.ProjectUpdatedEventBodyModel;
import com.omgservers.model.event.body.RuntimeCreatedEventBodyModel;
import com.omgservers.model.event.body.RuntimeDeletedEventBodyModel;
import com.omgservers.model.event.body.RuntimeUpdatedEventBodyModel;
import com.omgservers.model.event.body.SignInRequestedEventBodyModel;
import com.omgservers.model.event.body.SignUpRequestedEventBodyModel;
import com.omgservers.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.model.event.body.StageDeletedEventBodyModel;
import com.omgservers.model.event.body.StageUpdatedEventBodyModel;
import com.omgservers.model.event.body.TenantCreatedEventBodyModel;
import com.omgservers.model.event.body.TenantDeletedEventBodyModel;
import com.omgservers.model.event.body.VersionCreatedEventBodyModel;
import com.omgservers.model.event.body.VersionDeletedEventBodyModel;

public enum EventQualifierEnum {
    JOB_CREATED(JobCreatedEventBodyModel.class),
    JOB_UPDATED(JobUpdatedEventBodyModel.class),
    JOB_DELETED(JobDeletedEventBodyModel.class),
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
    PLAYER_CREATED(PlayerCreatedEventBodyModel.class),
    PLAYER_UPDATED(PlayerCreatedEventBodyModel.class),
    CLIENT_CREATED(ClientCreatedEventBodyModel.class),
    CLIENT_UPDATED(ClientUpdatedEventBodyModel.class),
    CLIENT_DISCONNECTED(ClientDisconnectedEventBodyModel.class),
    MATCHMAKER_CREATED(MatchmakerCreatedEventBodyModel.class),
    MATCHMAKER_UPDATED(MatchmakerUpdatedEventBodyModel.class),
    MATCHMAKER_DELETED(MatchmakerDeletedEventBodyModel.class),
    MATCH_CREATED(MatchCreatedEventBodyModel.class),
    MATCH_UPDATED(MatchUpdatedEventBodyModel.class),
    MATCH_DELETED(MatchDeletedEventBodyModel.class),
    MATCH_CLIENT_CREATED(MatchClientCreatedEventBodyModel.class),
    MATCH_CLIENT_DELETED(MatchClientDeletedEventBodyModel.class),
    RUNTIME_CREATED(RuntimeCreatedEventBodyModel.class),
    RUNTIME_UPDATED(RuntimeUpdatedEventBodyModel.class),
    RUNTIME_DELETED(RuntimeDeletedEventBodyModel.class),
    SIGN_UP_REQUESTED(SignUpRequestedEventBodyModel.class),
    SIGN_IN_REQUESTED(SignInRequestedEventBodyModel.class),
    MATCHMAKER_REQUESTED(MatchmakerRequestedEventBodyModel.class),
    PLAYER_SIGNED_UP(PlayerSignedUpEventBodyModel.class),
    PLAYER_SIGNED_IN(PlayerSignedInEventBodyModel.class);

    Class<? extends EventBodyModel> bodyClass;

    EventQualifierEnum(Class<? extends EventBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends EventBodyModel> getBodyClass() {
        return bodyClass;
    }
}
