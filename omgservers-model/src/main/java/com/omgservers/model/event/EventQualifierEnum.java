package com.omgservers.model.event;

import com.omgservers.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.model.event.body.ClientDeletedEventBodyModel;
import com.omgservers.model.event.body.ClientMessageReceivedEventBodyModel;
import com.omgservers.model.event.body.ClientRuntimeCreatedEventBodyModel;
import com.omgservers.model.event.body.ClientRuntimeDeletedEventBodyModel;
import com.omgservers.model.event.body.ContainerCreatedEventBodyModel;
import com.omgservers.model.event.body.ContainerDeletedEventBodyModel;
import com.omgservers.model.event.body.HandlerCreatedEventBodyModel;
import com.omgservers.model.event.body.HandlerDeletedEventBodyModel;
import com.omgservers.model.event.body.InactiveClientDetectedEventBodyModel;
import com.omgservers.model.event.body.IndexCreatedEventBodyModel;
import com.omgservers.model.event.body.IndexDeletedEventBodyModel;
import com.omgservers.model.event.body.JobCreatedEventBodyModel;
import com.omgservers.model.event.body.JobDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchClientCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchClientDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchDeletedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerDeletedEventBodyModel;
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
import com.omgservers.model.event.body.ServiceAccountCreatedEventBodyModel;
import com.omgservers.model.event.body.ServiceAccountDeletedEventBodyModel;
import com.omgservers.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.model.event.body.StageDeletedEventBodyModel;
import com.omgservers.model.event.body.TenantCreatedEventBodyModel;
import com.omgservers.model.event.body.TenantDeletedEventBodyModel;
import com.omgservers.model.event.body.UserCreatedEventBodyModel;
import com.omgservers.model.event.body.UserDeletedEventBodyModel;
import com.omgservers.model.event.body.VersionCreatedEventBodyModel;
import com.omgservers.model.event.body.VersionDeletedEventBodyModel;
import com.omgservers.model.event.body.VersionMatchmakerCreatedEventBodyModel;
import com.omgservers.model.event.body.VersionMatchmakerDeletedEventBodyModel;
import com.omgservers.model.event.body.VersionRuntimeCreatedEventBodyModel;
import com.omgservers.model.event.body.VersionRuntimeDeletedEventBodyModel;

public enum EventQualifierEnum {
    // System
    INDEX_CREATED(IndexCreatedEventBodyModel.class),
    INDEX_DELETED(IndexDeletedEventBodyModel.class),
    SERVICE_ACCOUNT_CREATED(ServiceAccountCreatedEventBodyModel.class),
    SERVICE_ACCOUNT_DELETED(ServiceAccountDeletedEventBodyModel.class),
    HANDLER_CREATED(HandlerCreatedEventBodyModel.class),
    HANDLER_DELETED(HandlerDeletedEventBodyModel.class),
    JOB_CREATED(JobCreatedEventBodyModel.class),
    JOB_DELETED(JobDeletedEventBodyModel.class),
    CONTAINER_CREATED(ContainerCreatedEventBodyModel.class),
    CONTAINER_DELETED(ContainerDeletedEventBodyModel.class),
    // Shard
    TENANT_CREATED(TenantCreatedEventBodyModel.class),
    TENANT_DELETED(TenantDeletedEventBodyModel.class),
    PROJECT_CREATED(ProjectCreatedEventBodyModel.class),
    PROJECT_DELETED(ProjectDeletedEventBodyModel.class),
    STAGE_CREATED(StageCreatedEventBodyModel.class),
    STAGE_DELETED(StageDeletedEventBodyModel.class),
    VERSION_MATCHMAKER_CREATED(VersionMatchmakerCreatedEventBodyModel.class),
    VERSION_MATCHMAKER_DELETED(VersionMatchmakerDeletedEventBodyModel.class),
    VERSION_RUNTIME_CREATED(VersionRuntimeCreatedEventBodyModel.class),
    VERSION_RUNTIME_DELETED(VersionRuntimeDeletedEventBodyModel.class),
    VERSION_CREATED(VersionCreatedEventBodyModel.class),
    VERSION_DELETED(VersionDeletedEventBodyModel.class),
    USER_CREATED(UserCreatedEventBodyModel.class),
    USER_DELETED(UserDeletedEventBodyModel.class),
    PLAYER_CREATED(PlayerCreatedEventBodyModel.class),
    PLAYER_DELETED(PlayerDeletedEventBodyModel.class),
    CLIENT_CREATED(ClientCreatedEventBodyModel.class),
    CLIENT_DELETED(ClientDeletedEventBodyModel.class),
    CLIENT_RUNTIME_CREATED(ClientRuntimeCreatedEventBodyModel.class),
    CLIENT_RUNTIME_DELETED(ClientRuntimeDeletedEventBodyModel.class),
    MATCHMAKER_CREATED(MatchmakerCreatedEventBodyModel.class),
    MATCHMAKER_DELETED(MatchmakerDeletedEventBodyModel.class),
    MATCH_CREATED(MatchCreatedEventBodyModel.class),
    MATCH_DELETED(MatchDeletedEventBodyModel.class),
    MATCH_CLIENT_CREATED(MatchClientCreatedEventBodyModel.class),
    MATCH_CLIENT_DELETED(MatchClientDeletedEventBodyModel.class),
    REQUEST_CREATED(RequestCreatedEventBodyModel.class),
    REQUEST_DELETED(MatchDeletedEventBodyModel.class),
    RUNTIME_CREATED(RuntimeCreatedEventBodyModel.class),
    RUNTIME_DELETED(RuntimeDeletedEventBodyModel.class),
    RUNTIME_CLIENT_CREATED(RuntimeClientCreatedEventBodyModel.class),
    RUNTIME_CLIENT_DELETED(RuntimeClientDeletedEventBodyModel.class),
    // Player
    CLIENT_MESSAGE_RECEIVED(ClientMessageReceivedEventBodyModel.class),
    MATCHMAKER_MESSAGE_RECEIVED(MatchmakerMessageReceivedEventBodyModel.class),
    // Internal
    INACTIVE_CLIENT_DETECTED(InactiveClientDetectedEventBodyModel.class);

    final Class<? extends EventBodyModel> bodyClass;

    EventQualifierEnum(Class<? extends EventBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends EventBodyModel> getBodyClass() {
        return bodyClass;
    }
}
