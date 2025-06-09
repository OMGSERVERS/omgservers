package com.omgservers.service.event;

import com.omgservers.service.event.body.internal.FailedClientDetectedEventBodyModel;
import com.omgservers.service.event.body.internal.InactiveClientDetectedEventBodyModel;
import com.omgservers.service.event.body.internal.InactiveRuntimeDetectedEventBodyModel;
import com.omgservers.service.event.body.module.alias.AliasCreatedEventBodyModel;
import com.omgservers.service.event.body.module.alias.AliasDeletedEventBodyModel;
import com.omgservers.service.event.body.module.client.ClientCreatedEventBodyModel;
import com.omgservers.service.event.body.module.client.ClientDeletedEventBodyModel;
import com.omgservers.service.event.body.module.client.ClientRuntimeRefCreatedEventBodyModel;
import com.omgservers.service.event.body.module.client.ClientRuntimeRefDeletedEventBodyModel;
import com.omgservers.service.event.body.module.deployment.DeploymentCreatedEventBodyModel;
import com.omgservers.service.event.body.module.deployment.DeploymentDeletedEventBodyModel;
import com.omgservers.service.event.body.module.deployment.DeploymentLobbyAssignmentCreatedEventBodyModel;
import com.omgservers.service.event.body.module.deployment.DeploymentLobbyAssignmentDeletedEventBodyModel;
import com.omgservers.service.event.body.module.deployment.DeploymentLobbyResourceCreatedEventBodyModel;
import com.omgservers.service.event.body.module.deployment.DeploymentLobbyResourceDeletedEventBodyModel;
import com.omgservers.service.event.body.module.deployment.DeploymentMatchmakerAssignmentCreatedEventBodyModel;
import com.omgservers.service.event.body.module.deployment.DeploymentMatchmakerAssignmentDeletedEventBodyModel;
import com.omgservers.service.event.body.module.deployment.DeploymentMatchmakerResourceCreatedEventBodyModel;
import com.omgservers.service.event.body.module.deployment.DeploymentMatchmakerResourceDeletedEventBodyModel;
import com.omgservers.service.event.body.module.lobby.LobbyCreatedEventBodyModel;
import com.omgservers.service.event.body.module.lobby.LobbyDeletedEventBodyModel;
import com.omgservers.service.event.body.module.match.MatchCreatedEventBodyModel;
import com.omgservers.service.event.body.module.match.MatchDeletedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerCreatedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerDeletedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchAssignmentCreatedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchAssignmentDeletedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchResourceCreatedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchResourceDeletedEventBodyModel;
import com.omgservers.service.event.body.module.pool.PoolContainerCreatedEventBodyModel;
import com.omgservers.service.event.body.module.pool.PoolContainerDeletedEventBodyModel;
import com.omgservers.service.event.body.module.pool.PoolCreatedEventBodyModel;
import com.omgservers.service.event.body.module.pool.PoolDeletedEventBodyModel;
import com.omgservers.service.event.body.module.pool.PoolServerCreatedEventBodyModel;
import com.omgservers.service.event.body.module.pool.PoolServerDeletedEventBodyModel;
import com.omgservers.service.event.body.module.runtime.RuntimeAssignmentCreatedEventBodyModel;
import com.omgservers.service.event.body.module.runtime.RuntimeAssignmentDeletedEventBodyModel;
import com.omgservers.service.event.body.module.runtime.RuntimeCreatedEventBodyModel;
import com.omgservers.service.event.body.module.runtime.RuntimeDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantDeploymentResourceCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantDeploymentResourceDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantImageCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantImageDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantProjectCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantProjectDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantStageCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantStageDeletedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantVersionCreatedEventBodyModel;
import com.omgservers.service.event.body.module.tenant.TenantVersionDeletedEventBodyModel;
import com.omgservers.service.event.body.module.user.PlayerCreatedEventBodyModel;
import com.omgservers.service.event.body.module.user.PlayerDeletedEventBodyModel;
import com.omgservers.service.event.body.module.user.UserCreatedEventBodyModel;
import com.omgservers.service.event.body.module.user.UserDeletedEventBodyModel;
import com.omgservers.service.event.body.system.IndexCreatedEventBodyModel;
import com.omgservers.service.event.body.system.TaskCreatedEventBodyModel;
import com.omgservers.service.event.body.system.TaskDeletedEventBodyModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventQualifierEnum {
    INDEX_CREATED(IndexCreatedEventBodyModel.class),
    TASK_CREATED(TaskCreatedEventBodyModel.class),
    TASK_DELETED(TaskDeletedEventBodyModel.class),
    ALIAS_CREATED(AliasCreatedEventBodyModel.class),
    ALIAS_DELETED(AliasDeletedEventBodyModel.class),
    POOL_CREATED(PoolCreatedEventBodyModel.class),
    POOL_DELETED(PoolDeletedEventBodyModel.class),
    POOL_SERVER_CREATED(PoolServerCreatedEventBodyModel.class),
    POOL_SERVER_DELETED(PoolServerDeletedEventBodyModel.class),
    POOL_CONTAINER_CREATED(PoolContainerCreatedEventBodyModel.class),
    POOL_CONTAINER_DELETED(PoolContainerDeletedEventBodyModel.class),
    TENANT_CREATED(TenantCreatedEventBodyModel.class),
    TENANT_DELETED(TenantDeletedEventBodyModel.class),
    TENANT_PROJECT_CREATED(TenantProjectCreatedEventBodyModel.class),
    TENANT_PROJECT_DELETED(TenantProjectDeletedEventBodyModel.class),
    TENANT_VERSION_CREATED(TenantVersionCreatedEventBodyModel.class),
    TENANT_VERSION_DELETED(TenantVersionDeletedEventBodyModel.class),
    TENANT_STAGE_CREATED(TenantStageCreatedEventBodyModel.class),
    TENANT_STAGE_DELETED(TenantStageDeletedEventBodyModel.class),
    TENANT_IMAGE_CREATED(TenantImageCreatedEventBodyModel.class),
    TENANT_IMAGE_DELETED(TenantImageDeletedEventBodyModel.class),
    TENANT_DEPLOYMENT_RESOURCE_CREATED(TenantDeploymentResourceCreatedEventBodyModel.class),
    TENANT_DEPLOYMENT_RESOURCE_DELETED(TenantDeploymentResourceDeletedEventBodyModel.class),
    DEPLOYMENT_CREATED(DeploymentCreatedEventBodyModel.class),
    DEPLOYMENT_DELETED(DeploymentDeletedEventBodyModel.class),
    DEPLOYMENT_LOBBY_RESOURCE_CREATED(DeploymentLobbyResourceCreatedEventBodyModel.class),
    DEPLOYMENT_LOBBY_RESOURCE_DELETED(DeploymentLobbyResourceDeletedEventBodyModel.class),
    DEPLOYMENT_LOBBY_ASSIGNMENT_CREATED(DeploymentLobbyAssignmentCreatedEventBodyModel.class),
    DEPLOYMENT_LOBBY_ASSIGNMENT_DELETED(DeploymentLobbyAssignmentDeletedEventBodyModel.class),
    DEPLOYMENT_MATCHMAKER_RESOURCE_CREATED(DeploymentMatchmakerResourceCreatedEventBodyModel.class),
    DEPLOYMENT_MATCHMAKER_RESOURCE_DELETED(DeploymentMatchmakerResourceDeletedEventBodyModel.class),
    DEPLOYMENT_MATCHMAKER_ASSIGNMENT_CREATED(DeploymentMatchmakerAssignmentCreatedEventBodyModel.class),
    DEPLOYMENT_MATCHMAKER_ASSIGNMENT_DELETED(DeploymentMatchmakerAssignmentDeletedEventBodyModel.class),
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
    MATCHMAKER_CREATED(MatchmakerCreatedEventBodyModel.class),
    MATCHMAKER_DELETED(MatchmakerDeletedEventBodyModel.class),
    MATCHMAKER_MATCH_RESOURCE_CREATED(MatchmakerMatchResourceCreatedEventBodyModel.class),
    MATCHMAKER_MATCH_RESOURCE_DELETED(MatchmakerMatchResourceDeletedEventBodyModel.class),
    MATCHMAKER_MATCH_ASSIGNMENT_CREATED(MatchmakerMatchAssignmentCreatedEventBodyModel.class),
    MATCHMAKER_MATCH_ASSIGNMENT_DELETED(MatchmakerMatchAssignmentDeletedEventBodyModel.class),
    MATCH_CREATED(MatchCreatedEventBodyModel.class),
    MATCH_DELETED(MatchDeletedEventBodyModel.class),
    RUNTIME_CREATED(RuntimeCreatedEventBodyModel.class),
    RUNTIME_DELETED(RuntimeDeletedEventBodyModel.class),
    RUNTIME_ASSIGNMENT_CREATED(RuntimeAssignmentCreatedEventBodyModel.class),
    RUNTIME_ASSIGNMENT_DELETED(RuntimeAssignmentDeletedEventBodyModel.class),
    // Internal
    INACTIVE_CLIENT_DETECTED(InactiveClientDetectedEventBodyModel.class),
    FAILED_CLIENT_DETECTED(FailedClientDetectedEventBodyModel.class),
    INACTIVE_RUNTIME_DETECTED(InactiveRuntimeDetectedEventBodyModel.class);

    final Class<? extends EventBodyModel> bodyClass;
}
