package com.omgservers.service.shard.queue.impl.service.webService.impl.api;

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
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Queue Shard API")
@Path("/service/v1/shard/queue/request")
public interface QueueApi {

    /*
    Queue
     */

    @POST
    @Path("/get-queue")
    Uni<GetQueueResponse> execute(GetQueueRequest request);

    @POST
    @Path("/sync-queue")
    Uni<SyncQueueResponse> execute(SyncQueueRequest request);

    @POST
    @Path("/delete-queue")
    Uni<DeleteQueueResponse> execute(DeleteQueueRequest request);

    /*
    QueueRequest
     */

    @POST
    @Path("/get-queue-request")
    Uni<GetQueueRequestResponse> execute(GetQueueRequestRequest request);

    @POST
    @Path("/find-queue-request")
    Uni<FindQueueRequestResponse> execute(FindQueueRequestRequest request);

    @POST
    @Path("/view-queue-requests")
    Uni<ViewQueueRequestsResponse> execute(ViewQueueRequestsRequest request);

    @POST
    @Path("/sync-queue-request")
    Uni<SyncQueueRequestResponse> execute(SyncQueueRequestRequest request);

    @POST
    @Path("/delete-queue-request")
    Uni<DeleteQueueRequestResponse> execute(DeleteQueueRequestRequest request);
}
