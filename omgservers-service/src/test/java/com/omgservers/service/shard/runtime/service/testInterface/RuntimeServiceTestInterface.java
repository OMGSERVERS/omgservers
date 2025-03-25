package com.omgservers.service.shard.runtime.service.testInterface;

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
