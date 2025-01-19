package com.omgservers.service.module.queue.impl.service.queueService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.queue.queue.DeleteQueueRequest;
import com.omgservers.schema.module.queue.queue.DeleteQueueResponse;
import com.omgservers.schema.module.queue.queue.GetQueueRequest;
import com.omgservers.schema.module.queue.queue.GetQueueResponse;
import com.omgservers.schema.module.queue.queue.SyncQueueRequest;
import com.omgservers.schema.module.queue.queue.SyncQueueResponse;
import com.omgservers.schema.module.queue.queueRequest.DeleteQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.DeleteQueueRequestResponse;
import com.omgservers.schema.module.queue.queueRequest.FindQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.FindQueueRequestResponse;
import com.omgservers.schema.module.queue.queueRequest.GetQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.GetQueueRequestResponse;
import com.omgservers.schema.module.queue.queueRequest.SyncQueueRequestRequest;
import com.omgservers.schema.module.queue.queueRequest.SyncQueueRequestResponse;
import com.omgservers.schema.module.queue.queueRequest.ViewQueueRequestsRequest;
import com.omgservers.schema.module.queue.queueRequest.ViewQueueRequestsResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.queue.impl.operation.getQueueModuleClient.GetQueueModuleClientOperation;
import com.omgservers.service.module.queue.impl.service.queueService.QueueService;
import com.omgservers.service.module.queue.impl.service.queueService.impl.method.queue.DeleteQueueMethod;
import com.omgservers.service.module.queue.impl.service.queueService.impl.method.queue.GetQueueMethod;
import com.omgservers.service.module.queue.impl.service.queueService.impl.method.queue.SyncQueueMethod;
import com.omgservers.service.module.queue.impl.service.queueService.impl.method.queueRequest.DeleteQueueRequestMethod;
import com.omgservers.service.module.queue.impl.service.queueService.impl.method.queueRequest.FindQueueRequestMethod;
import com.omgservers.service.module.queue.impl.service.queueService.impl.method.queueRequest.GetQueueRequestMethod;
import com.omgservers.service.module.queue.impl.service.queueService.impl.method.queueRequest.SyncQueueRequestMethod;
import com.omgservers.service.module.queue.impl.service.queueService.impl.method.queueRequest.ViewQueueRequestsMethod;
import com.omgservers.service.module.queue.impl.service.webService.impl.api.QueueApi;
import com.omgservers.service.operation.server.CalculateShardOperation;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class QueueServiceImpl implements QueueService {

    final DeleteQueueRequestMethod deleteQueueRequestMethod;
    final ViewQueueRequestsMethod viewQueueRequestsMethod;
    final FindQueueRequestMethod findQueueRequestMethod;
    final SyncQueueRequestMethod syncQueueRequestMethod;
    final GetQueueRequestMethod getQueueRequestMethod;
    final DeleteQueueMethod deleteQueueMethod;
    final SyncQueueMethod syncQueueMethod;
    final GetQueueMethod getQueueMethod;

    final HandleShardedRequestOperation handleShardedRequestOperation;
    final GetQueueModuleClientOperation getQueueModuleClientOperation;
    final CalculateShardOperation calculateShardOperation;

    /*
    Queue
     */

    @Override
    public Uni<GetQueueResponse> execute(@Valid final GetQueueRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getQueueModuleClientOperation::getClient,
                QueueApi::execute,
                getQueueMethod::execute);
    }

    @Override
    public Uni<SyncQueueResponse> execute(@Valid final SyncQueueRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getQueueModuleClientOperation::getClient,
                QueueApi::execute,
                syncQueueMethod::execute);
    }

    @Override
    public Uni<SyncQueueResponse> executeWithIdempotency(@Valid final SyncQueueRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getQueue(), t.getMessage());
                            return Uni.createFrom().item(new SyncQueueResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteQueueResponse> execute(@Valid final DeleteQueueRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getQueueModuleClientOperation::getClient,
                QueueApi::execute,
                deleteQueueMethod::execute);
    }

    /*
    QueueRequest
     */

    @Override
    public Uni<GetQueueRequestResponse> execute(@Valid final GetQueueRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getQueueModuleClientOperation::getClient,
                QueueApi::execute,
                getQueueRequestMethod::execute);
    }

    @Override
    public Uni<FindQueueRequestResponse> execute(@Valid final FindQueueRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getQueueModuleClientOperation::getClient,
                QueueApi::execute,
                findQueueRequestMethod::execute);
    }

    @Override
    public Uni<ViewQueueRequestsResponse> execute(@Valid final ViewQueueRequestsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getQueueModuleClientOperation::getClient,
                QueueApi::execute,
                viewQueueRequestsMethod::execute);
    }

    @Override
    public Uni<SyncQueueRequestResponse> execute(@Valid final SyncQueueRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getQueueModuleClientOperation::getClient,
                QueueApi::execute,
                syncQueueRequestMethod::execute);
    }

    @Override
    public Uni<SyncQueueRequestResponse> executeWithIdempotency(SyncQueueRequestRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}",
                                    request.getQueueRequest(), t.getMessage());
                            return Uni.createFrom().item(new SyncQueueRequestResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteQueueRequestResponse> execute(@Valid final DeleteQueueRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getQueueModuleClientOperation::getClient,
                QueueApi::execute,
                deleteQueueRequestMethod::execute);
    }
}
