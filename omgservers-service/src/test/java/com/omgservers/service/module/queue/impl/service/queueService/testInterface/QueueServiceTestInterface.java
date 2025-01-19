package com.omgservers.service.module.queue.impl.service.queueService.testInterface;

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
import com.omgservers.service.module.queue.impl.service.queueService.QueueService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class QueueServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final QueueService queueService;

    public GetQueueResponse execute(final GetQueueRequest request) {
        return queueService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncQueueResponse execute(final SyncQueueRequest request) {
        return queueService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncQueueResponse executeWithIdempotency(final SyncQueueRequest request) {
        return queueService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteQueueResponse execute(final DeleteQueueRequest request) {
        return queueService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetQueueRequestResponse execute(final GetQueueRequestRequest request) {
        return queueService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindQueueRequestResponse execute(final FindQueueRequestRequest request) {
        return queueService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewQueueRequestsResponse execute(final ViewQueueRequestsRequest request) {
        return queueService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncQueueRequestResponse execute(final SyncQueueRequestRequest request) {
        return queueService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncQueueRequestResponse executeWithIdempotency(final SyncQueueRequestRequest request) {
        return queueService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteQueueRequestResponse execute(final DeleteQueueRequestRequest request) {
        return queueService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
