package com.omgservers.service.master.task.impl.service.webService.impl.api;

import com.omgservers.schema.master.task.DeleteTaskRequest;
import com.omgservers.schema.master.task.DeleteTaskResponse;
import com.omgservers.schema.master.task.FindTaskRequest;
import com.omgservers.schema.master.task.FindTaskResponse;
import com.omgservers.schema.master.task.GetTaskRequest;
import com.omgservers.schema.master.task.GetTaskResponse;
import com.omgservers.schema.master.task.SyncTaskRequest;
import com.omgservers.schema.master.task.SyncTaskResponse;
import com.omgservers.schema.master.task.ViewTasksRequest;
import com.omgservers.schema.master.task.ViewTasksResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Task Master API")
@Path("/service/v1/master/task/request")
public interface TaskApi {

    @POST
    @Path("/get-task")
    Uni<GetTaskResponse> execute(GetTaskRequest request);

    @POST
    @Path("/find-task")
    Uni<FindTaskResponse> execute(FindTaskRequest request);

    @POST
    @Path("/view-tasks")
    Uni<ViewTasksResponse> execute(ViewTasksRequest request);

    @POST
    @Path("/sync-task")
    Uni<SyncTaskResponse> execute(SyncTaskRequest request);

    @POST
    @Path("/delete-task")
    Uni<DeleteTaskResponse> execute(DeleteTaskRequest request);
}
