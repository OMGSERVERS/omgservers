package com.omgservers.service.shard.runtime.impl.service.webService;

import com.omgservers.schema.shard.runtime.runtime.DeleteRuntimeRequest;
import com.omgservers.schema.shard.runtime.runtime.DeleteRuntimeResponse;
import com.omgservers.schema.shard.runtime.runtime.GetRuntimeRequest;
import com.omgservers.schema.shard.runtime.runtime.GetRuntimeResponse;
import com.omgservers.schema.shard.runtime.runtime.SyncRuntimeRequest;
import com.omgservers.schema.shard.runtime.runtime.SyncRuntimeResponse;
import com.omgservers.schema.shard.runtime.runtimeAssignment.DeleteRuntimeAssignmentRequest;
import com.omgservers.schema.shard.runtime.runtimeAssignment.DeleteRuntimeAssignmentResponse;
import com.omgservers.schema.shard.runtime.runtimeAssignment.FindRuntimeAssignmentRequest;
import com.omgservers.schema.shard.runtime.runtimeAssignment.FindRuntimeAssignmentResponse;
import com.omgservers.schema.shard.runtime.runtimeAssignment.GetRuntimeAssignmentRequest;
import com.omgservers.schema.shard.runtime.runtimeAssignment.GetRuntimeAssignmentResponse;
import com.omgservers.schema.shard.runtime.runtimeAssignment.SyncRuntimeAssignmentRequest;
import com.omgservers.schema.shard.runtime.runtimeAssignment.SyncRuntimeAssignmentResponse;
import com.omgservers.schema.shard.runtime.runtimeAssignment.ViewRuntimeAssignmentsRequest;
import com.omgservers.schema.shard.runtime.runtimeAssignment.ViewRuntimeAssignmentsResponse;
import com.omgservers.schema.shard.runtime.runtimeCommand.DeleteRuntimeCommandRequest;
import com.omgservers.schema.shard.runtime.runtimeCommand.DeleteRuntimeCommandResponse;
import com.omgservers.schema.shard.runtime.runtimeCommand.SyncRuntimeCommandRequest;
import com.omgservers.schema.shard.runtime.runtimeCommand.SyncRuntimeCommandResponse;
import com.omgservers.schema.shard.runtime.runtimeCommand.ViewRuntimeCommandsRequest;
import com.omgservers.schema.shard.runtime.runtimeCommand.ViewRuntimeCommandsResponse;
import com.omgservers.schema.shard.runtime.runtimeMessage.DeleteRuntimeMessageRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.DeleteRuntimeMessageResponse;
import com.omgservers.schema.shard.runtime.runtimeMessage.DeleteRuntimeMessagesRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.DeleteRuntimeMessagesResponse;
import com.omgservers.schema.shard.runtime.runtimeMessage.InterchangeMessagesRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.InterchangeMessagesResponse;
import com.omgservers.schema.shard.runtime.runtimeMessage.SyncRuntimeMessageRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.SyncRuntimeMessageResponse;
import com.omgservers.schema.shard.runtime.runtimeMessage.ViewRuntimeMessagesRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.ViewRuntimeMessagesResponse;
import com.omgservers.schema.shard.runtime.runtimeState.GetRuntimeStateRequest;
import com.omgservers.schema.shard.runtime.runtimeState.GetRuntimeStateResponse;
import com.omgservers.schema.shard.runtime.runtimeState.UpdateRuntimeStateRequest;
import com.omgservers.schema.shard.runtime.runtimeState.UpdateRuntimeStateResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    /*
    Runtime
     */

    Uni<GetRuntimeResponse> execute(GetRuntimeRequest request);

    Uni<SyncRuntimeResponse> execute(SyncRuntimeRequest request);

    Uni<DeleteRuntimeResponse> execute(DeleteRuntimeRequest request);

    /*
    RuntimeCommand
     */

    Uni<ViewRuntimeCommandsResponse> execute(ViewRuntimeCommandsRequest request);

    Uni<SyncRuntimeCommandResponse> execute(SyncRuntimeCommandRequest request);

    Uni<DeleteRuntimeCommandResponse> execute(DeleteRuntimeCommandRequest request);

    /*
    RuntimeMessage
     */

    Uni<ViewRuntimeMessagesResponse> execute(ViewRuntimeMessagesRequest request);

    Uni<SyncRuntimeMessageResponse> execute(SyncRuntimeMessageRequest request);

    Uni<DeleteRuntimeMessageResponse> execute(DeleteRuntimeMessageRequest request);

    Uni<DeleteRuntimeMessagesResponse> execute(DeleteRuntimeMessagesRequest request);

    Uni<InterchangeMessagesResponse> execute(InterchangeMessagesRequest request);

    /*
    RuntimeAssignment
     */

    Uni<GetRuntimeAssignmentResponse> execute(GetRuntimeAssignmentRequest request);

    Uni<FindRuntimeAssignmentResponse> execute(FindRuntimeAssignmentRequest request);

    Uni<ViewRuntimeAssignmentsResponse> execute(ViewRuntimeAssignmentsRequest request);

    Uni<SyncRuntimeAssignmentResponse> execute(SyncRuntimeAssignmentRequest request);

    Uni<DeleteRuntimeAssignmentResponse> execute(DeleteRuntimeAssignmentRequest request);

    /*
    RuntimeState
     */

    Uni<GetRuntimeStateResponse> execute(GetRuntimeStateRequest request);

    Uni<UpdateRuntimeStateResponse> execute(UpdateRuntimeStateRequest request);
}
