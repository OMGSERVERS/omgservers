package com.omgservers.application.module.internalModule.model.event;

import com.omgservers.application.module.internalModule.model.event.body.*;

public enum EventQualifierEnum {
    EVENT_CREATED(EventCreatedEventBodyModel.class),
    JOB_CREATED(JobCreatedEventBodyModel.class),
    JOB_DELETED(JobDeletedEventBodyModel.class),
    TENANT_CREATED(TenantCreatedEventBodyModel.class),
    TENANT_DELETED(TenantDeletedEventBodyModel.class),
    PROJECT_CREATED(ProjectCreatedEventBodyModel.class),
    PROJECT_DELETED(ProjectDeletedEventBodyModel.class),
    STAGE_CREATED(StageCreatedEventBodyModel.class),
    STAGE_DELETED(StageDeletedEventBodyModel.class),
    VERSION_CREATED(VersionCreatedEventBodyModel.class),
    PLAYER_CREATED(PlayerCreatedEventBodyModel.class),
    CLIENT_CREATED(ClientCreatedEventBodyModel.class),
    MATCHMAKER_CREATED(MatchmakerCreatedEventBodyModel.class),
    MATCHMAKER_DELETED(MatchmakerDeletedEventBodyModel.class),
    MATCH_CREATED(MatchCreatedEventBodyModel.class),
    MATCH_DELETED(MatchDeletedEventBodyModel.class),
    RUNTIME_CREATED(RuntimeCreatedEventBodyModel.class),
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
