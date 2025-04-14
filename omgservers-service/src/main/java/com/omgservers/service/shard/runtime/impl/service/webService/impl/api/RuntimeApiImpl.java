package com.omgservers.service.shard.runtime.impl.service.webService.impl.api;

import com.omgservers.schema.model.user.UserRoleEnum;
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
import com.omgservers.service.operation.server.HandleApiRequestOperation;
import com.omgservers.service.shard.runtime.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RolesAllowed({UserRoleEnum.Names.SERVICE})
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeApiImpl implements RuntimeApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    /*
    Runtime
     */

    @Override
    public Uni<GetRuntimeResponse> execute(final GetRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncRuntimeResponse> execute(final SyncRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteRuntimeResponse> execute(final DeleteRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    RuntimeCommand
     */

    @Override
    public Uni<ViewRuntimeCommandsResponse> execute(final ViewRuntimeCommandsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncRuntimeCommandResponse> execute(final SyncRuntimeCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteRuntimeCommandResponse> execute(final DeleteRuntimeCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    RuntimeMessage
     */

    @Override
    public Uni<ViewRuntimeMessagesResponse> execute(final ViewRuntimeMessagesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncRuntimeMessageResponse> execute(final SyncRuntimeMessageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteRuntimeMessageResponse> execute(final DeleteRuntimeMessageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteRuntimeMessagesResponse> execute(final DeleteRuntimeMessagesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<InterchangeMessagesResponse> execute(final InterchangeMessagesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    RuntimeAssignment
     */

    @Override
    public Uni<GetRuntimeAssignmentResponse> execute(final GetRuntimeAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindRuntimeAssignmentResponse> execute(final FindRuntimeAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewRuntimeAssignmentsResponse> execute(final ViewRuntimeAssignmentsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncRuntimeAssignmentResponse> execute(final SyncRuntimeAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteRuntimeAssignmentResponse> execute(final DeleteRuntimeAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    RuntimeState
     */

    @Override
    public Uni<GetRuntimeStateResponse> execute(final GetRuntimeStateRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<UpdateRuntimeStateResponse> execute(final UpdateRuntimeStateRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
