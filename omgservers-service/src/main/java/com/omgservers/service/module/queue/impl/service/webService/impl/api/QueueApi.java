package com.omgservers.service.module.queue.impl.service.webService.impl.api;

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
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Queue Module API")
@Path("/service/v1/module/queue/request")
public interface QueueApi {

    /*
    Queue
     */

    @PUT
    @Path("/get-queue")
    Uni<GetQueueResponse> execute(GetQueueRequest request);

    @PUT
    @Path("/sync-queue")
    Uni<SyncQueueResponse> execute(SyncQueueRequest request);

    @PUT
    @Path("/delete-queue")
    Uni<DeleteQueueResponse> execute(DeleteQueueRequest request);

    /*
    QueueRequest
     */

    @PUT
    @Path("/get-queue-request")
    Uni<GetQueueRequestResponse> execute(GetQueueRequestRequest request);

    @PUT
    @Path("/find-queue-request")
    Uni<FindQueueRequestResponse> execute(FindQueueRequestRequest request);

    @PUT
    @Path("/view-queue-requests")
    Uni<ViewQueueRequestsResponse> execute(ViewQueueRequestsRequest request);

    @PUT
    @Path("/sync-queue-request")
    Uni<SyncQueueRequestResponse> execute(SyncQueueRequestRequest request);

    @PUT
    @Path("/delete-queue-request")
    Uni<DeleteQueueRequestResponse> execute(DeleteQueueRequestRequest request);
}
