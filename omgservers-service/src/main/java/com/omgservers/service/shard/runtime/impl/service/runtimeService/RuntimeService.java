package com.omgservers.service.shard.runtime.impl.service.runtimeService;

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
import jakarta.validation.Valid;

public interface RuntimeService {

    /*
    Runtime
     */

    Uni<GetRuntimeResponse> execute(@Valid GetRuntimeRequest request);

    Uni<SyncRuntimeResponse> execute(@Valid SyncRuntimeRequest request);

    Uni<SyncRuntimeResponse> executeWithIdempotency(@Valid SyncRuntimeRequest request);

    Uni<DeleteRuntimeResponse> execute(@Valid DeleteRuntimeRequest request);

    /*
    RuntimeCommand
     */

    Uni<ViewRuntimeCommandsResponse> execute(@Valid ViewRuntimeCommandsRequest request);

    Uni<SyncRuntimeCommandResponse> execute(@Valid SyncRuntimeCommandRequest request);

    Uni<SyncRuntimeCommandResponse> executeWithIdempotency(@Valid SyncRuntimeCommandRequest request);

    Uni<DeleteRuntimeCommandResponse> execute(@Valid DeleteRuntimeCommandRequest request);

    /*
    RuntimeMessage
     */

    Uni<ViewRuntimeMessagesResponse> execute(@Valid ViewRuntimeMessagesRequest request);

    Uni<SyncRuntimeMessageResponse> execute(@Valid SyncRuntimeMessageRequest request);

    Uni<SyncRuntimeMessageResponse> executeWithIdempotency(@Valid SyncRuntimeMessageRequest request);

    Uni<DeleteRuntimeMessageResponse> execute(@Valid DeleteRuntimeMessageRequest request);

    Uni<DeleteRuntimeMessagesResponse> execute(@Valid DeleteRuntimeMessagesRequest request);

    Uni<InterchangeMessagesResponse> execute(@Valid InterchangeMessagesRequest request);

    /*
    RuntimeAssignment
     */

    Uni<GetRuntimeAssignmentResponse> execute(@Valid GetRuntimeAssignmentRequest request);

    Uni<FindRuntimeAssignmentResponse> execute(@Valid FindRuntimeAssignmentRequest request);

    Uni<ViewRuntimeAssignmentsResponse> execute(@Valid ViewRuntimeAssignmentsRequest request);

    Uni<SyncRuntimeAssignmentResponse> execute(@Valid SyncRuntimeAssignmentRequest request);

    Uni<SyncRuntimeAssignmentResponse> executeWithIdempotency(@Valid SyncRuntimeAssignmentRequest request);

    Uni<DeleteRuntimeAssignmentResponse> execute(@Valid DeleteRuntimeAssignmentRequest request);

    /*
    RuntimeState
     */

    Uni<GetRuntimeStateResponse> execute(@Valid GetRuntimeStateRequest request);

    Uni<UpdateRuntimeStateResponse> execute(@Valid UpdateRuntimeStateRequest request);

}
