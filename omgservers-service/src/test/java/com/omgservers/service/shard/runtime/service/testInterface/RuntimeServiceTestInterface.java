package com.omgservers.service.shard.runtime.service.testInterface;

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
import com.omgservers.service.shard.runtime.impl.service.runtimeService.RuntimeService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final RuntimeService runtimeService;

    /*
    Runtime
     */

    public GetRuntimeResponse execute(final GetRuntimeRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimeResponse execute(final SyncRuntimeRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimeResponse execute(final DeleteRuntimeRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    RuntimeCommand
     */

    public ViewRuntimeCommandsResponse execute(final ViewRuntimeCommandsRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimeCommandResponse execute(final SyncRuntimeCommandRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimeCommandResponse executeWithIdempotency(final SyncRuntimeCommandRequest request) {
        return runtimeService.executeWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimeCommandResponse execute(final DeleteRuntimeCommandRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    RuntimeMessage
     */

    public ViewRuntimeMessagesResponse execute(final ViewRuntimeMessagesRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimeMessageResponse execute(final SyncRuntimeMessageRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimeMessageResponse executeWithIdempotency(final SyncRuntimeMessageRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimeMessageResponse execute(final DeleteRuntimeMessageRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimeMessagesResponse execute(final DeleteRuntimeMessagesRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public InterchangeMessagesResponse execute(final InterchangeMessagesRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    RuntimeAssignment
     */

    public GetRuntimeAssignmentResponse execute(final GetRuntimeAssignmentRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindRuntimeAssignmentResponse execute(final FindRuntimeAssignmentRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewRuntimeAssignmentsResponse execute(final ViewRuntimeAssignmentsRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimeAssignmentResponse execute(final SyncRuntimeAssignmentRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimeAssignmentResponse executeWithIdempotency(final SyncRuntimeAssignmentRequest request) {
        return runtimeService.executeWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimeAssignmentResponse execute(final DeleteRuntimeAssignmentRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    RuntimeState
     */

    public GetRuntimeStateResponse execute(final GetRuntimeStateRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public UpdateRuntimeStateResponse execute(final UpdateRuntimeStateRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
