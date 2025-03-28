package com.omgservers.service.handler.impl.runtime;

import com.omgservers.schema.model.job.JobModel;
import com.omgservers.schema.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import com.omgservers.schema.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.schema.model.runtimePoolContainerRef.RuntimePoolContainerRefModel;
import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefResponse;
import com.omgservers.schema.module.lobby.FindLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.FindLobbyRuntimeRefResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRuntimeRefResponse;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
import com.omgservers.schema.module.pool.poolContainer.DeletePoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.DeletePoolContainerResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.schema.module.runtime.ViewRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimeAssignmentsResponse;
import com.omgservers.schema.module.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.schema.module.runtime.poolContainerRef.FindRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.FindRuntimePoolContainerRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.runtime.RuntimeDeletedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.lobby.LobbyShard;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.shard.pool.PoolShard;
import com.omgservers.service.shard.runtime.RuntimeShard;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.shard.user.UserShard;
import com.omgservers.service.operation.server.GetServersOperation;
import com.omgservers.service.service.job.JobService;
import com.omgservers.service.service.job.dto.DeleteJobRequest;
import com.omgservers.service.service.job.dto.DeleteJobResponse;
import com.omgservers.service.service.job.dto.FindJobRequest;
import com.omgservers.service.service.job.dto.FindJobResponse;
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

    final MatchmakerShard matchmakerShard;
    final RuntimeShard runtimeShard;
    final TenantShard tenantShard;
    final LobbyShard lobbyShard;
    final UserShard userShard;
    final PoolShard poolShard;

    final JobService jobService;

    final GetServersOperation getServersOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (RuntimeDeletedEventBodyModel) event.getBody();
        final var runtimeId = body.getId();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    log.debug("Deleted, {}", runtime);

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
        return runtimeShard.getService().execute(request)
                .map(GetRuntimeResponse::getRuntime);
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
        return runtimeShard.getService().execute(request)
                .map(ViewRuntimeCommandsResponse::getRuntimeCommands);
    }

    Uni<Boolean> deleteRuntimeCommand(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimeCommandRequest(runtimeId, id);
        return runtimeShard.getService().execute(request)
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
        return runtimeShard.getService().execute(request)
                .map(DeleteRuntimeAssignmentResponse::getDeleted);
    }

    Uni<List<RuntimeAssignmentModel>> viewRuntimeAssignments(final Long runtimeId) {
        final var request = new ViewRuntimeAssignmentsRequest(runtimeId);
        return runtimeShard.getService().execute(request)
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
        return lobbyShard.getService().findLobbyRuntimeRef(request)
                .map(FindLobbyRuntimeRefResponse::getLobbyRuntimeRef);
    }

    Uni<Boolean> deleteLobbyRuntimeRef(final Long lobbyId, final Long id) {
        final var request = new DeleteLobbyRuntimeRefRequest(lobbyId, id);
        return lobbyShard.getService().deleteLobbyRuntimeRef(request)
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
        return matchmakerShard.getService().execute(request)
                .map(FindMatchmakerMatchRuntimeRefResponse::getMatchRuntimeRef);
    }

    Uni<Boolean> deleteMatchRuntimeRef(final Long matchmakerId,
                                       final Long matchId,
                                       final Long id) {
        final var request = new DeleteMatchmakerMatchRuntimeRefRequest(matchmakerId, matchId, id);
        return matchmakerShard.getService().execute(request)
                .map(DeleteMatchmakerMatchRuntimeRefResponse::getDeleted);
    }

    Uni<Void> deleteRuntimeContainer(final Long runtimeId) {
        return findPoolRuntimeServerContainerRef(runtimeId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(this::deletePoolContainer)
                .replaceWithVoid();
    }

    Uni<RuntimePoolContainerRefModel> findPoolRuntimeServerContainerRef(final Long runtimeId) {
        final var request = new FindRuntimePoolContainerRefRequest(runtimeId);
        return runtimeShard.getService().execute(request)
                .map(FindRuntimePoolContainerRefResponse::getRuntimePoolContainerRef);
    }

    Uni<Boolean> deletePoolContainer(final RuntimePoolContainerRefModel runtimePoolContainerRef) {
        final var poolId = runtimePoolContainerRef.getPoolId();
        final var serverId = runtimePoolContainerRef.getServerId();
        final var containerId = runtimePoolContainerRef.getContainerId();
        final var request = new DeletePoolContainerRequest(poolId, serverId, containerId);
        return poolShard.getPoolService().execute(request)
                .map(DeletePoolContainerResponse::getDeleted);
    }

    Uni<Void> findAndDeleteJob(final Long tenantId) {
        return findJob(tenantId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(job -> deleteJob(job.getId()))
                .replaceWithVoid();
    }

    Uni<JobModel> findJob(final Long tenantId) {
        final var request = new FindJobRequest(tenantId, tenantId);
        return jobService.findJob(request)
                .map(FindJobResponse::getJob);
    }

    Uni<Boolean> deleteJob(final Long id) {
        final var request = new DeleteJobRequest(id);
        return jobService.deleteJob(request)
                .map(DeleteJobResponse::getDeleted);
    }
}
