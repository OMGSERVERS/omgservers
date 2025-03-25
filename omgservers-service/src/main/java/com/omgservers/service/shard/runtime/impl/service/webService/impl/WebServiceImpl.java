package com.omgservers.service.shard.runtime.impl.service.webService.impl;

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
import com.omgservers.service.shard.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.service.shard.runtime.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final RuntimeService runtimeService;

    /*
    Runtime
     */

    @Override
    public Uni<GetRuntimeResponse> execute(final GetRuntimeRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<SyncRuntimeResponse> execute(final SyncRuntimeRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<DeleteRuntimeResponse> execute(final DeleteRuntimeRequest request) {
        return runtimeService.execute(request);
    }

    /*
    RuntimeCommand
     */

    @Override
    public Uni<ViewRuntimeCommandsResponse> execute(final ViewRuntimeCommandsRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<SyncRuntimeCommandResponse> execute(final SyncRuntimeCommandRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<DeleteRuntimeCommandResponse> execute(final DeleteRuntimeCommandRequest request) {
        return runtimeService.execute(request);
    }

    /*
    RuntimeMessage
     */

    @Override
    public Uni<ViewRuntimeMessagesResponse> execute(final ViewRuntimeMessagesRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<SyncRuntimeMessageResponse> execute(final SyncRuntimeMessageRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<DeleteRuntimeMessageResponse> execute(final DeleteRuntimeMessageRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<DeleteRuntimeMessagesResponse> execute(final DeleteRuntimeMessagesRequest request) {
        return runtimeService.execute(request);
    }

    /*
    RuntimeAssignment
     */

    @Override
    public Uni<GetRuntimeAssignmentResponse> execute(final GetRuntimeAssignmentRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<FindRuntimeAssignmentResponse> execute(final FindRuntimeAssignmentRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<ViewRuntimeAssignmentsResponse> execute(final ViewRuntimeAssignmentsRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<SyncRuntimeAssignmentResponse> execute(final SyncRuntimeAssignmentRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<DeleteRuntimeAssignmentResponse> execute(final DeleteRuntimeAssignmentRequest request) {
        return runtimeService.execute(request);
    }
    
    @Override
    public Uni<InterchangeMessagesResponse> execute(final InterchangeMessagesRequest request) {
        return runtimeService.execute(request);
    }

    /*
    RuntimeState
     */

    @Override
    public Uni<GetRuntimeStateResponse> execute(final GetRuntimeStateRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<UpdateRuntimeStateResponse> execute(final UpdateRuntimeStateRequest request) {
        return runtimeService.execute(request);
    }
}
