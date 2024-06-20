package com.omgservers.service.handler.runtime;

import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRefResponse;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.pool.poolServerContainer.DeletePoolServerContainerRequest;
import com.omgservers.model.dto.pool.poolServerContainer.DeletePoolServerContainerResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeAssignmentResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeAssignmentsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeAssignmentsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsResponse;
import com.omgservers.model.dto.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefRequest;
import com.omgservers.model.dto.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefResponse;
import com.omgservers.model.dto.system.job.DeleteJobRequest;
import com.omgservers.model.dto.system.job.DeleteJobResponse;
import com.omgservers.model.dto.system.job.FindJobRequest;
import com.omgservers.model.dto.system.job.FindJobResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.runtime.RuntimeDeletedEventBodyModel;
import com.omgservers.model.job.JobModel;
import com.omgservers.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import com.omgservers.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimePermission.RuntimePermissionModel;
import com.omgservers.model.runtimePoolServerContainerRef.RuntimePoolServerContainerRefModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.getServers.GetServersOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;
    final SystemModule systemModule;
    final LobbyModule lobbyModule;
    final UserModule userModule;
    final PoolModule poolModule;

    final GetServersOperation getServersOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (RuntimeDeletedEventBodyModel) event.getBody();
        final var runtimeId = body.getId();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    log.info("Runtime was deleted, " +
                                    "id={}, " +
                                    "type={}, " +
                                    "version={}/{}",
                            runtime.getId(),
                            runtime.getQualifier(),
                            runtime.getTenantId(),
                            runtime.getVersionId());

                    // TODO: cleanup container user
                    return deleteRuntimeCommands(runtimeId)
                            .flatMap(voidItem -> deleteRuntimeAssignments(runtimeId))
                            .flatMap(voidItem -> deleteRuntimeRef(runtime))
                            .flatMap(voidItem -> deleteRuntimeContainer(runtimeId))
                            .flatMap(voidItem -> findAndDeleteJob(runtimeId));
                })
                .replaceWithVoid();
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<List<RuntimePermissionModel>> viewRuntimePermissions(final Long runtimeId) {
        final var request = new ViewRuntimePermissionsRequest(runtimeId);
        return runtimeModule.getRuntimeService().viewRuntimePermissions(request)
                .map(ViewRuntimePermissionsResponse::getRuntimePermissions);
    }

    Uni<Boolean> deleteRuntimePermission(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimePermissionRequest(runtimeId, id);
        return runtimeModule.getRuntimeService().deleteRuntimePermission(request)
                .map(DeleteRuntimePermissionResponse::getDeleted);
    }

    Uni<Void> deleteRuntimeCommands(final Long runtimeId) {
        return viewRuntimeCommands(runtimeId)
                .flatMap(runtimeCommands -> Multi.createFrom().iterable(runtimeCommands)
                        .onItem().transformToUniAndConcatenate(runtimeCommand ->
                                deleteRuntimeCommand(runtimeId, runtimeCommand.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete runtime command failed, " +
                                                            "runtimeId={}, " +
                                                            "runtimeCommandId={}" +
                                                            "{}:{}",
                                                    runtimeId,
                                                    runtimeCommand.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<RuntimeCommandModel>> viewRuntimeCommands(final Long runtimeId) {
        final var request = new ViewRuntimeCommandsRequest(runtimeId);
        return runtimeModule.getRuntimeService().viewRuntimeCommands(request)
                .map(ViewRuntimeCommandsResponse::getRuntimeCommands);
    }

    Uni<Boolean> deleteRuntimeCommand(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimeCommandRequest(runtimeId, id);
        return runtimeModule.getRuntimeService().deleteRuntimeCommand(request)
                .map(DeleteRuntimeCommandResponse::getDeleted);
    }

    Uni<Void> deleteRuntimeAssignments(final Long runtimeId) {
        return viewRuntimeAssignments(runtimeId)
                .flatMap(runtimeAssignments -> Multi.createFrom().iterable(runtimeAssignments)
                        .onItem().transformToUniAndConcatenate(runtimeAssignment ->
                                deleteRuntimeAssignment(runtimeId, runtimeAssignment.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete runtime assignment failed, " +
                                                            "runtimeId={}, " +
                                                            "runtimeAssignmentId={}" +
                                                            "{}:{}",
                                                    runtimeId,
                                                    runtimeAssignment.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<Boolean> deleteRuntimeAssignment(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimeAssignmentRequest(runtimeId, id);
        return runtimeModule.getRuntimeService().deleteRuntimeAssignment(request)
                .map(DeleteRuntimeAssignmentResponse::getDeleted);
    }

    Uni<List<RuntimeAssignmentModel>> viewRuntimeAssignments(final Long runtimeId) {
        final var request = new ViewRuntimeAssignmentsRequest(runtimeId);
        return runtimeModule.getRuntimeService().viewRuntimeAssignments(request)
                .map(ViewRuntimeAssignmentsResponse::getRuntimeAssignments);
    }

    Uni<Void> deleteRuntimeRef(final RuntimeModel runtime) {
        return switch (runtime.getQualifier()) {
            case LOBBY -> {
                final var lobbyId = runtime.getConfig().getLobbyConfig().getLobbyId();
                yield findAndDeleteLobbyRuntimeRef(lobbyId);
            }
            case MATCH -> {
                final var matchConfig = runtime.getConfig().getMatchConfig();
                final var matchmakerId = matchConfig.getMatchmakerId();
                final var matchId = matchConfig.getMatchId();

                yield findAndDeleteMatchRuntimeRef(matchmakerId, matchId);
            }
        };
    }

    Uni<Void> findAndDeleteLobbyRuntimeRef(final Long lobbyId) {
        return findLobbyRuntimeRef(lobbyId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(lobbyRuntimeRef ->
                        deleteLobbyRuntimeRef(lobbyId, lobbyRuntimeRef.getId()))
                .replaceWithVoid();
    }

    Uni<LobbyRuntimeRefModel> findLobbyRuntimeRef(final Long lobbyId) {
        final var request = new FindLobbyRuntimeRefRequest(lobbyId);
        return lobbyModule.getLobbyService().findLobbyRuntimeRef(request)
                .map(FindLobbyRuntimeRefResponse::getLobbyRuntimeRef);
    }

    Uni<Boolean> deleteLobbyRuntimeRef(final Long lobbyId, final Long id) {
        final var request = new DeleteLobbyRuntimeRefRequest(lobbyId, id);
        return lobbyModule.getLobbyService().deleteLobbyRuntimeRef(request)
                .map(DeleteLobbyRuntimeRefResponse::getDeleted);
    }

    Uni<Void> findAndDeleteMatchRuntimeRef(final Long matchmakerId,
                                           final Long matchId) {
        return findMatchRuntimeRef(matchmakerId, matchId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(matchRuntimeRef ->
                        deleteMatchRuntimeRef(matchmakerId, matchId, matchRuntimeRef.getId()))
                .replaceWithVoid();
    }

    Uni<MatchmakerMatchRuntimeRefModel> findMatchRuntimeRef(final Long matchmakerId,
                                                            final Long matchId) {
        final var request = new FindMatchmakerMatchRuntimeRefRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().findMatchmakerMatchRuntimeRef(request)
                .map(FindMatchmakerMatchRuntimeRefResponse::getMatchRuntimeRef);
    }

    Uni<Boolean> deleteMatchRuntimeRef(final Long matchmakerId,
                                       final Long matchId,
                                       final Long id) {
        final var request = new DeleteMatchmakerMatchRuntimeRefRequest(matchmakerId, matchId, id);
        return matchmakerModule.getMatchmakerService().deleteMatchmakerMatchRuntimeRef(request)
                .map(DeleteMatchmakerMatchRuntimeRefResponse::getDeleted);
    }

    Uni<Void> deleteRuntimeContainer(final Long runtimeId) {
        return findPoolRuntimeServerContainerRef(runtimeId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(this::deletePoolServerContainer)
                .replaceWithVoid();
    }

    Uni<RuntimePoolServerContainerRefModel> findPoolRuntimeServerContainerRef(final Long runtimeId) {
        final var request = new FindRuntimePoolServerContainerRefRequest(runtimeId);
        return runtimeModule.getRuntimeService().findRuntimePoolServerContainerRef(request)
                .map(FindRuntimePoolServerContainerRefResponse::getRuntimePoolServerContainerRef);
    }

    Uni<Boolean> deletePoolServerContainer(RuntimePoolServerContainerRefModel runtimePoolServerContainerRef) {
        final var poolId = runtimePoolServerContainerRef.getPoolId();
        final var serverId = runtimePoolServerContainerRef.getServerId();
        final var containerId = runtimePoolServerContainerRef.getContainerId();
        final var request = new DeletePoolServerContainerRequest(poolId, serverId, containerId);
        return poolModule.getPoolService().deletePoolServerContainer(request)
                .map(DeletePoolServerContainerResponse::getDeleted);
    }

    Uni<Void> findAndDeleteJob(final Long tenantId) {
        return findJob(tenantId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(job -> deleteJob(job.getId()))
                .replaceWithVoid();
    }

    Uni<JobModel> findJob(final Long tenantId) {
        final var request = new FindJobRequest(tenantId);
        return systemModule.getJobService().findJob(request)
                .map(FindJobResponse::getJob);
    }

    Uni<Boolean> deleteJob(final Long id) {
        final var request = new DeleteJobRequest(id);
        return systemModule.getJobService().deleteJob(request)
                .map(DeleteJobResponse::getDeleted);
    }
}
