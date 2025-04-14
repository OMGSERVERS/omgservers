package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.shard.deployment.deployment.*;
import com.omgservers.schema.shard.deployment.deploymentCommand.*;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.*;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.*;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.*;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.*;
import com.omgservers.schema.shard.deployment.deploymentRequest.*;
import com.omgservers.schema.shard.deployment.deploymentState.GetDeploymentStateRequest;
import com.omgservers.schema.shard.deployment.deploymentState.GetDeploymentStateResponse;
import com.omgservers.schema.shard.deployment.deploymentState.UpdateDeploymentStateRequest;
import com.omgservers.schema.shard.deployment.deploymentState.UpdateDeploymentStateResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.CalculateShardOperation;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
import com.omgservers.service.shard.deployment.impl.operation.getDeploymentModuleClient.DeploymentModuleClient;
import com.omgservers.service.shard.deployment.impl.operation.getDeploymentModuleClient.GetDeploymentModuleClientOperation;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.DeploymentService;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deployment.DeleteDeploymentMethod;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deployment.GetDeploymentDataMethod;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deployment.GetDeploymentMethod;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deployment.SyncDeploymentMethod;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentCommand.DeleteDeploymentCommandMethod;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentCommand.SyncDeploymentCommandMethod;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentCommand.ViewDeploymentCommandsMethod;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyAsignment.*;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyResource.*;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerAssignment.*;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerResource.*;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentRequest.*;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentState.GetDeploymentStateMethod;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentState.UpdateDeploymentStateMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DeploymentServiceImpl implements DeploymentService {

    final UpdateDeploymentMatchmakerResourceStatusMethod updateDeploymentMatchmakerResourceStatusMethod;
    final DeleteDeploymentMatchmakerAssignmentMethod deleteDeploymentMatchmakerAssignmentMethod;
    final UpdateDeploymentLobbyResourceStatusMethod updateDeploymentLobbyResourceStatusMethod;
    final FindDeploymentMatchmakerAssignmentMethod findDeploymentMatchmakerAssignmentMethod;
    final SyncDeploymentMatchmakerAssignmentMethod syncDeploymentMatchmakerAssignmentMethod;
    final ViewDeploymentMatchmakerAssignmentMethod viewDeploymentMatchmakerAssignmentMethod;
    final DeleteDeploymentMatchmakerResourceMethod deleteDeploymentMatchmakerResourceMethod;
    final GetDeploymentMatchmakerAssignmentMethod getDeploymentMatchmakerAssignmentMethod;
    final ViewDeploymentMatchmakerResourcesMethod viewDeploymentMatchmakerResourcesMethod;
    final FindDeploymentMatchmakerResourceMethod findDeploymentMatchmakerResourceMethod;
    final SyncDeploymentMatchmakerResourceMethod syncDeploymentMatchmakerResourceMethod;
    final GetDeploymentMatchmakerResourceMethod getDeploymentMatchmakerResourceMethod;
    final DeleteDeploymentLobbyAssignmentMethod deleteDeploymentLobbyAssignmentMethod;
    final DeleteDeploymentLobbyResourceMethod deleteDeploymentLobbyResourceMethod;
    final FindDeploymentLobbyAssignmentMethod findDeploymentLobbyAssignmentMethod;
    final SyncDeploymentLobbyAssignmentMethod syncDeploymentLobbyAssignmentMethod;
    final ViewDeploymentLobbyAssignmentMethod viewDeploymentLobbyAssignmentMethod;
    final ViewDeploymentLobbyResourcesMethod viewDeploymentLobbyResourcesMethod;
    final GetDeploymentLobbyAssignmentMethod getDeploymentLobbyAssignmentMethod;
    final FindDeploymentLobbyResourceMethod findDeploymentLobbyResourceMethod;
    final SyncDeploymentLobbyResourceMethod syncDeploymentLobbyResourceMethod;
    final GetDeploymentLobbyResourceMethod getDeploymentLobbyResourceMethod;
    final DeleteDeploymentCommandMethod deleteDeploymentCommandMethod;
    final DeleteDeploymentRequestMethod deleteDeploymentRequestMethod;
    final ViewDeploymentRequestsMethod viewDeploymentRequestsMethod;
    final ViewDeploymentCommandsMethod viewDeploymentCommandsMethod;
    final FindDeploymentRequestMethod findDeploymentRequestMethod;
    final SyncDeploymentRequestMethod syncDeploymentRequestMethod;
    final SyncDeploymentCommandMethod syncDeploymentCommandMethod;
    final UpdateDeploymentStateMethod updateDeploymentStateMethod;
    final GetDeploymentRequestMethod getDeploymentRequestMethod;
    final GetDeploymentStateMethod getDeploymentStateMethod;
    final GetDeploymentDataMethod getDeploymentDataMethod;
    final DeleteDeploymentMethod deleteDeploymentMethod;
    final SyncDeploymentMethod syncDeploymentMethod;
    final GetDeploymentMethod getDeploymentMethod;

    final GetDeploymentModuleClientOperation getDeploymentModuleClientOperation;
    final HandleShardedRequestOperation handleShardedRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    /*
    Deployment
     */

    @Override
    public Uni<GetDeploymentResponse> execute(@Valid final GetDeploymentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                getDeploymentMethod::execute);
    }

    @Override
    public Uni<GetDeploymentDataResponse> execute(GetDeploymentDataRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                getDeploymentDataMethod::execute);
    }

    @Override
    public Uni<SyncDeploymentResponse> execute(@Valid final SyncDeploymentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                syncDeploymentMethod::execute);
    }

    @Override
    public Uni<SyncDeploymentResponse> executeWithIdempotency(SyncDeploymentRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}",
                                    request.getDeployment(), t.getMessage());
                            return Uni.createFrom().item(new SyncDeploymentResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteDeploymentResponse> execute(
            @Valid final DeleteDeploymentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                deleteDeploymentMethod::execute);
    }

    /*
    DeploymentCommand
     */

    @Override
    public Uni<ViewDeploymentCommandsResponse> execute(@Valid final ViewDeploymentCommandsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                viewDeploymentCommandsMethod::execute);
    }

    @Override
    public Uni<SyncDeploymentCommandResponse> execute(@Valid final SyncDeploymentCommandRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                syncDeploymentCommandMethod::execute);
    }

    @Override
    public Uni<SyncDeploymentCommandResponse> executeWithIdempotency(SyncDeploymentCommandRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}",
                                    request.getDeploymentCommand(), t.getMessage());
                            return Uni.createFrom().item(new SyncDeploymentCommandResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteDeploymentCommandResponse> execute(
            @Valid final DeleteDeploymentCommandRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                deleteDeploymentCommandMethod::execute);
    }

    /*
    DeploymentRequest
     */

    @Override
    public Uni<GetDeploymentRequestResponse> execute(@Valid final GetDeploymentRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                getDeploymentRequestMethod::execute);
    }

    @Override
    public Uni<FindDeploymentRequestResponse> execute(@Valid final FindDeploymentRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                findDeploymentRequestMethod::execute);
    }

    @Override
    public Uni<ViewDeploymentRequestsResponse> execute(@Valid final ViewDeploymentRequestsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                viewDeploymentRequestsMethod::execute);
    }

    @Override
    public Uni<SyncDeploymentRequestResponse> execute(@Valid final SyncDeploymentRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                syncDeploymentRequestMethod::execute);
    }

    @Override
    public Uni<SyncDeploymentRequestResponse> executeWithIdempotency(SyncDeploymentRequestRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}",
                                    request.getDeploymentRequest(), t.getMessage());
                            return Uni.createFrom().item(new SyncDeploymentRequestResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteDeploymentRequestResponse> execute(@Valid final DeleteDeploymentRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                deleteDeploymentRequestMethod::execute);
    }

    /*
    LobbyResource
     */

    @Override
    public Uni<GetDeploymentLobbyResourceResponse> execute(
            @Valid final GetDeploymentLobbyResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                getDeploymentLobbyResourceMethod::execute);
    }

    @Override
    public Uni<FindDeploymentLobbyResourceResponse> execute(
            @Valid final FindDeploymentLobbyResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                findDeploymentLobbyResourceMethod::execute);
    }

    @Override
    public Uni<ViewDeploymentLobbyResourcesResponse> execute(
            @Valid final ViewDeploymentLobbyResourcesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                viewDeploymentLobbyResourcesMethod::execute);
    }

    @Override
    public Uni<SyncDeploymentLobbyResourceResponse> execute(
            @Valid final SyncDeploymentLobbyResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                syncDeploymentLobbyResourceMethod::execute);
    }

    @Override
    public Uni<UpdateDeploymentLobbyResourceStatusResponse> execute(
            @Valid final UpdateDeploymentLobbyResourceStatusRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                updateDeploymentLobbyResourceStatusMethod::execute);
    }

    @Override
    public Uni<SyncDeploymentLobbyResourceResponse> executeWithIdempotency(
            @Valid final SyncDeploymentLobbyResourceRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getDeploymentLobbyResource(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncDeploymentLobbyResourceResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteDeploymentLobbyResourceResponse> execute(
            @Valid final DeleteDeploymentLobbyResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                deleteDeploymentLobbyResourceMethod::execute);
    }

    /*
    DeploymentLobbyAssignment
     */

    @Override
    public Uni<GetDeploymentLobbyAssignmentResponse> execute(@Valid final GetDeploymentLobbyAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                getDeploymentLobbyAssignmentMethod::execute);
    }

    @Override
    public Uni<FindDeploymentLobbyAssignmentResponse> execute(@Valid final FindDeploymentLobbyAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                findDeploymentLobbyAssignmentMethod::execute);
    }

    @Override
    public Uni<ViewDeploymentLobbyAssignmentsResponse> execute(@Valid final ViewDeploymentLobbyAssignmentsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                viewDeploymentLobbyAssignmentMethod::execute);
    }

    @Override
    public Uni<SyncDeploymentLobbyAssignmentResponse> execute(@Valid final SyncDeploymentLobbyAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                syncDeploymentLobbyAssignmentMethod::execute);
    }

    @Override
    public Uni<DeleteDeploymentLobbyAssignmentResponse> execute(@Valid final DeleteDeploymentLobbyAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                deleteDeploymentLobbyAssignmentMethod::execute);
    }

    /*
    MatchmakerResource
     */

    @Override
    public Uni<GetDeploymentMatchmakerResourceResponse> execute(@Valid final GetDeploymentMatchmakerResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                getDeploymentMatchmakerResourceMethod::execute);
    }

    @Override
    public Uni<FindDeploymentMatchmakerResourceResponse> execute(@Valid final FindDeploymentMatchmakerResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                findDeploymentMatchmakerResourceMethod::execute);
    }

    @Override
    public Uni<ViewDeploymentMatchmakerResourcesResponse> execute(@Valid final ViewDeploymentMatchmakerResourcesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                viewDeploymentMatchmakerResourcesMethod::execute);
    }

    @Override
    public Uni<SyncDeploymentMatchmakerResourceResponse> execute(@Valid final SyncDeploymentMatchmakerResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                syncDeploymentMatchmakerResourceMethod::execute);
    }

    @Override
    public Uni<SyncDeploymentMatchmakerResourceResponse> executeWithIdempotency(@Valid final SyncDeploymentMatchmakerResourceRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getDeploymentMatchmakerResource(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncDeploymentMatchmakerResourceResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<UpdateDeploymentMatchmakerResourceStatusResponse> execute(
            @Valid final UpdateDeploymentMatchmakerResourceStatusRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                updateDeploymentMatchmakerResourceStatusMethod::execute);
    }

    @Override
    public Uni<DeleteDeploymentMatchmakerResourceResponse> execute(@Valid final DeleteDeploymentMatchmakerResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                deleteDeploymentMatchmakerResourceMethod::execute);
    }

    /*
    DeploymentMatchmakerAssignment
     */

    @Override
    public Uni<DeleteDeploymentMatchmakerAssignmentResponse> execute(@Valid final DeleteDeploymentMatchmakerAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                deleteDeploymentMatchmakerAssignmentMethod::execute);
    }

    @Override
    public Uni<SyncDeploymentMatchmakerAssignmentResponse> execute(@Valid final SyncDeploymentMatchmakerAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                syncDeploymentMatchmakerAssignmentMethod::execute);
    }

    @Override
    public Uni<ViewDeploymentMatchmakerAssignmentsResponse> execute(@Valid final ViewDeploymentMatchmakerAssignmentsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                viewDeploymentMatchmakerAssignmentMethod::execute);
    }

    @Override
    public Uni<FindDeploymentMatchmakerAssignmentResponse> execute(@Valid final FindDeploymentMatchmakerAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                findDeploymentMatchmakerAssignmentMethod::execute);
    }

    @Override
    public Uni<GetDeploymentMatchmakerAssignmentResponse> execute(@Valid final GetDeploymentMatchmakerAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                getDeploymentMatchmakerAssignmentMethod::execute);
    }

    /*
    DeploymentState
     */

    @Override
    public Uni<GetDeploymentStateResponse> execute(@Valid final GetDeploymentStateRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                getDeploymentStateMethod::execute);
    }

    @Override
    public Uni<UpdateDeploymentStateResponse> execute(@Valid final UpdateDeploymentStateRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getDeploymentModuleClientOperation::getClient,
                DeploymentModuleClient::execute,
                updateDeploymentStateMethod::execute);
    }
}
