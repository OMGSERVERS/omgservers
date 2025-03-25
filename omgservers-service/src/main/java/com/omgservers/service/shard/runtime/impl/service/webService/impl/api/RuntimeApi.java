package com.omgservers.service.shard.runtime.impl.service.webService.impl.api;

import com.omgservers.schema.module.runtime.runtime.DeleteRuntimeRequest;
import com.omgservers.schema.module.runtime.runtime.DeleteRuntimeResponse;
import com.omgservers.schema.module.runtime.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.runtime.GetRuntimeResponse;
import com.omgservers.schema.module.runtime.runtime.SyncRuntimeRequest;
import com.omgservers.schema.module.runtime.runtime.SyncRuntimeResponse;
import com.omgservers.schema.module.runtime.runtimeAssignment.DeleteRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.DeleteRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.runtimeAssignment.FindRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.FindRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.runtimeAssignment.GetRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.GetRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.runtimeAssignment.SyncRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.SyncRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.runtimeAssignment.ViewRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.ViewRuntimeAssignmentsResponse;
import com.omgservers.schema.module.runtime.runtimeCommand.DeleteRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.runtimeCommand.DeleteRuntimeCommandResponse;
import com.omgservers.schema.module.runtime.runtimeCommand.SyncRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.runtimeCommand.SyncRuntimeCommandResponse;
import com.omgservers.schema.module.runtime.runtimeCommand.ViewRuntimeCommandsRequest;
import com.omgservers.schema.module.runtime.runtimeCommand.ViewRuntimeCommandsResponse;
import com.omgservers.schema.module.runtime.runtimeMessage.DeleteRuntimeMessageRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.DeleteRuntimeMessageResponse;
import com.omgservers.schema.module.runtime.runtimeMessage.DeleteRuntimeMessagesRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.DeleteRuntimeMessagesResponse;
import com.omgservers.schema.module.runtime.runtimeMessage.InterchangeMessagesRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.InterchangeMessagesResponse;
import com.omgservers.schema.module.runtime.runtimeMessage.SyncRuntimeMessageRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.SyncRuntimeMessageResponse;
import com.omgservers.schema.module.runtime.runtimeMessage.ViewRuntimeMessagesRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.ViewRuntimeMessagesResponse;
import com.omgservers.schema.module.runtime.runtimeState.GetRuntimeStateRequest;
import com.omgservers.schema.module.runtime.runtimeState.GetRuntimeStateResponse;
import com.omgservers.schema.module.runtime.runtimeState.UpdateRuntimeStateRequest;
import com.omgservers.schema.module.runtime.runtimeState.UpdateRuntimeStateResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Runtime Shard API")
@Path("/service/v1/shard/runtime/request")
public interface RuntimeApi {

    /*
    Runtime
     */

    @POST
    @Path("/get-runtime")
    Uni<GetRuntimeResponse> execute(GetRuntimeRequest request);

    @POST
    @Path("/sync-runtime")
    Uni<SyncRuntimeResponse> execute(SyncRuntimeRequest request);

    @POST
    @Path("/delete-runtime")
    Uni<DeleteRuntimeResponse> execute(DeleteRuntimeRequest request);

    /*
    RuntimeCommands
     */

    @POST
    @Path("/view-runtime-commands")
    Uni<ViewRuntimeCommandsResponse> execute(ViewRuntimeCommandsRequest request);

    @POST
    @Path("/sync-runtime-command")
    Uni<SyncRuntimeCommandResponse> execute(SyncRuntimeCommandRequest request);

    @POST
    @Path("/delete-runtime-command")
    Uni<DeleteRuntimeCommandResponse> execute(DeleteRuntimeCommandRequest request);

    /*
    RuntimeMessage
     */

    @POST
    @Path("/view-runtime-messages")
    Uni<ViewRuntimeMessagesResponse> execute(ViewRuntimeMessagesRequest request);

    @POST
    @Path("/sync-runtime-message")
    Uni<SyncRuntimeMessageResponse> execute(SyncRuntimeMessageRequest request);

    @POST
    @Path("/delete-runtime-message")
    Uni<DeleteRuntimeMessageResponse> execute(DeleteRuntimeMessageRequest request);

    @POST
    @Path("/delete-runtime-messages")
    Uni<DeleteRuntimeMessagesResponse> execute(DeleteRuntimeMessagesRequest request);

    @POST
    @Path("/interchange-messages")
    Uni<InterchangeMessagesResponse> execute(InterchangeMessagesRequest request);

    /*
    RuntimeAssignment
     */

    @POST
    @Path("/get-runtime-assignment")
    Uni<GetRuntimeAssignmentResponse> execute(GetRuntimeAssignmentRequest request);

    @POST
    @Path("/find-runtime-assignment")
    Uni<FindRuntimeAssignmentResponse> execute(FindRuntimeAssignmentRequest request);

    @POST
    @Path("/view-runtime-assignment")
    Uni<ViewRuntimeAssignmentsResponse> execute(ViewRuntimeAssignmentsRequest request);

    @POST
    @Path("/sync-runtime-assignment")
    Uni<SyncRuntimeAssignmentResponse> execute(SyncRuntimeAssignmentRequest request);

    @POST
    @Path("/delete-runtime-assignment")
    Uni<DeleteRuntimeAssignmentResponse> execute(DeleteRuntimeAssignmentRequest request);

    /*
    RuntimeState
     */

    @POST
    @Path("/get-runtime-state")
    Uni<GetRuntimeStateResponse> execute(GetRuntimeStateRequest request);

    @POST
    @Path("/update-runtime-state")
    Uni<UpdateRuntimeStateResponse> execute(UpdateRuntimeStateRequest request);
}
