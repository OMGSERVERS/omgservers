package com.omgservers.service.service.task.impl.method.executeQueueTask;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.queue.queueRequest.DeleteQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.DeleteQueueRequestResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.queue.QueueModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.assignment.AssignLobbyOperation;
import com.omgservers.service.operation.assignment.SelectRandomLobbyOperation;
import com.omgservers.service.operation.lobby.DeleteDanglingLobbiesOperation;
import com.omgservers.service.operation.lobby.EnsureOneLobbyOperation;
import com.omgservers.service.operation.queue.FetchQueueOperation;
import com.omgservers.service.operation.queue.FetchedQueue;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class QueueTaskImpl {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;
    final QueueModule queueModule;

    final DeleteDanglingLobbiesOperation deleteDanglingLobbiesOperation;
    final SelectRandomLobbyOperation selectRandomLobbyOperation;
    final EnsureOneLobbyOperation ensureOneLobbyOperation;
    final AssignLobbyOperation assignLobbyOperation;
    final FetchQueueOperation fetchQueueOperation;

    public Uni<Boolean> execute(final Long queueId) {
        return fetchQueueOperation.execute(queueId)
                .flatMap(this::handleQueue)
                .replaceWith(Boolean.TRUE);
    }

    Uni<Void> handleQueue(final FetchedQueue fetchedQueue) {
        final var queue = fetchedQueue.queue();
        final var queueId = queue.getId();
        final var queueRequests = fetchedQueue.queueRequests();

        if (queueRequests.isEmpty()) {
            log.trace("The queue \"{}\" has no requests to process", queueId);

            final var tenantId = queue.getTenantId();
            final var deploymentId = queue.getDeploymentId();
            return deleteDanglingLobbiesOperation.execute(tenantId, deploymentId);
        } else {
            return handleRequests(fetchedQueue);
        }
    }

    Uni<Void> handleRequests(final FetchedQueue fetchedQueue) {
        final var queue = fetchedQueue.queue();
        final var queueId = queue.getId();
        final var tenantId = queue.getTenantId();
        final var tenantDeploymentId = queue.getDeploymentId();
        final var queueRequests = fetchedQueue.queueRequests();

        return selectRandomLobbyOperation.execute(tenantId, tenantDeploymentId)
                .flatMap(randomSelectedLobby -> Multi.createFrom().iterable(queueRequests)
                        .onItem().transformToUniAndConcatenate(queueRequest -> {
                            final var clientId = queueRequest.getClientId();
                            final var idempotencyKey = queueRequest.getId().toString();
                            return assignLobbyOperation.execute(clientId,
                                            randomSelectedLobby,
                                            idempotencyKey)
                                    .flatMap(voidItem -> {
                                        final var queueRequestId = queueRequest.getId();
                                        return deleteQueueRequest(queueId, queueRequestId);
                                    });
                        })
                        .collect().asList()
                        .replaceWithVoid())
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideNotFoundException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.LOBBY_NOT_FOUND)) {
                            return ensureOneLobbyOperation.execute(tenantId, tenantDeploymentId);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    Uni<Boolean> deleteQueueRequest(final Long queueId,
                                    final Long queueRequestId) {
        final var request = new DeleteQueueRequestRequest(queueId, queueRequestId);
        return queueModule.getQueueService().execute(request)
                .map(DeleteQueueRequestResponse::getDeleted);
    }
}
