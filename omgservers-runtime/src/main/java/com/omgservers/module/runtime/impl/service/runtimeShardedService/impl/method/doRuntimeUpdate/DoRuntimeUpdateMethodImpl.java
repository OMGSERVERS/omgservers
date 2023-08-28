package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.doRuntimeUpdate;

import com.omgservers.dto.handler.HandleAddActorRuntimeCommandRequest;
import com.omgservers.dto.handler.HandleDeleteActorRuntimeCommandRequest;
import com.omgservers.dto.handler.HandleHandleIncomingRuntimeCommandRequest;
import com.omgservers.dto.handler.HandleInitRuntimeCommandRequest;
import com.omgservers.dto.handler.HandleStopRuntimeCommandRequest;
import com.omgservers.dto.runtime.DoRuntimeUpdateShardedRequest;
import com.omgservers.dto.runtime.DoRuntimeUpdateShardedResponse;
import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedResponse;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import com.omgservers.model.runtimeCommand.body.AddActorRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.DeleteActorRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.HandleIncomingRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.InitRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.StopRuntimeCommandBodyModel;
import com.omgservers.module.context.ContextModule;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.runtime.impl.operation.selectNewRuntimeCommands.SelectNewRuntimeCommandsOperation;
import com.omgservers.module.runtime.impl.operation.upsertRuntime.UpsertRuntimeOperation;
import com.omgservers.module.runtime.impl.operation.upsertRuntimeCommand.UpsertRuntimeCommandOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoRuntimeUpdateMethodImpl implements DoRuntimeUpdateMethod {

    final RuntimeModule runtimeModule;
    final ContextModule contextModule;

    final SelectNewRuntimeCommandsOperation selectNewRuntimeCommandsOperation;
    final UpsertRuntimeCommandOperation upsertRuntimeCommandOperation;
    final UpsertRuntimeOperation upsertRuntimeOperation;
    final CheckShardOperation checkShardOperation;
    final GetConfigOperation getConfigOperation;

    final PgPool pgPool;

    @Override
    public Uni<DoRuntimeUpdateShardedResponse> doRuntimeUpdate(final DoRuntimeUpdateShardedRequest request) {
        DoRuntimeUpdateShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var runtimeId = request.getRuntimeId();
                    return getRuntime(runtimeId)
                            .flatMap(runtime -> pgPool.withTransaction(sqlConnection ->
                                    updateRuntime(sqlConnection, shardModel.shard(), runtime)))
                            .map(updateRuntimeResult -> {
                                final var affectedCommands = updateRuntimeResult.affectedCommands;
                                final var doRuntimeUpdateShardedResponse = new DoRuntimeUpdateShardedResponse();
                                doRuntimeUpdateShardedResponse.setHandledCommands(affectedCommands.size());
                                if (getConfigOperation.getConfig().verbose()) {
                                    final var extendedResponse = new DoRuntimeUpdateShardedResponse.ExtendedResponse(affectedCommands);
                                    doRuntimeUpdateShardedResponse.setExtendedResponse(extendedResponse);
                                }
                                return doRuntimeUpdateShardedResponse;
                            });
                });
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeShardedRequest(id);
        return runtimeModule.getRuntimeShardedService().getRuntime(request)
                .map(GetRuntimeShardedResponse::getRuntime);
    }

    Uni<UpdateRuntimeResult> updateRuntime(final SqlConnection sqlConnection,
                                           final int shard,
                                           final RuntimeModel runtime) {
        final var runtimeId = runtime.getId();
        runtime.setCurrentStep(runtime.getCurrentStep() + 1);
        return selectNewRuntimeCommandsOperation.selectNewRuntimeCommands(sqlConnection, shard, runtimeId)
                .flatMap(runtimeCommands -> handleRuntimeCommands(sqlConnection, shard, runtimeCommands, runtime.getCurrentStep())
                        .flatMap(affectedCommands -> upsertRuntimeOperation.upsertRuntime(sqlConnection, shard, runtime)
                                .map(inserted -> new UpdateRuntimeResult(affectedCommands))));
    }

    Uni<List<RuntimeCommandModel>> handleRuntimeCommands(final SqlConnection sqlConnection,
                                                         final int shard,
                                                         final List<RuntimeCommandModel> runtimeCommands,
                                                         final Long currentStep) {
        return Multi.createFrom().iterable(runtimeCommands)
                .onItem().transformToUniAndConcatenate(runtimeCommand ->
                        handleRuntimeCommand(sqlConnection, shard, runtimeCommand, currentStep))
                .collect().asList();
    }

    Uni<RuntimeCommandModel> handleRuntimeCommand(final SqlConnection sqlConnection,
                                                  final int shard,
                                                  final RuntimeCommandModel runtimeCommand,
                                                  final Long currentStep) {
        runtimeCommand.setStep(currentStep);
        return callContextHandler(runtimeCommand)
                .flatMap(voidItem -> upsertRuntimeCommandOperation
                        .upsertRuntimeCommand(sqlConnection, shard, runtimeCommand))
                .replaceWith(runtimeCommand);
    }

    Uni<Void> callContextHandler(final RuntimeCommandModel runtimeCommand) {
        final var qualifier = runtimeCommand.getQualifier();
        final var commandBody = runtimeCommand.getBody();

        if (qualifier.getBodyClass().isInstance(commandBody)) {
            // TODO: take result from context handler
            runtimeCommand.setStatus(RuntimeCommandStatusEnum.PROCESSED);
            final var runtimeId = runtimeCommand.getRuntimeId();
            final var contextService = contextModule.getContextService();
            switch (qualifier) {
                case INIT_RUNTIME -> {
                    final var initRuntimeCommandBody = (InitRuntimeCommandBodyModel) commandBody;
                    final var handleInitRuntimeCommandRequest = new HandleInitRuntimeCommandRequest(runtimeId);
                    return contextService.handleInitRuntimeCommand(handleInitRuntimeCommandRequest);
                }
                case STOP_RUNTIME -> {
                    final var stopRuntimeCommandBody = (StopRuntimeCommandBodyModel) commandBody;
                    final var handleStopRuntimeCommandRequest = new HandleStopRuntimeCommandRequest(runtimeId);
                    return contextService.handleStopRuntimeCommand(handleStopRuntimeCommandRequest);
                }
                case ADD_ACTOR -> {
                    final var addActorRuntimeCommandBody = (AddActorRuntimeCommandBodyModel) commandBody;
                    final var userId = addActorRuntimeCommandBody.getUserId();
                    final var playerId = addActorRuntimeCommandBody.getPlayerId();
                    final var clientId = addActorRuntimeCommandBody.getClientId();
                    final var handleAddActorRuntimeCommandRequest =
                            new HandleAddActorRuntimeCommandRequest(runtimeId, userId, playerId, clientId);
                    return contextService.handleAddActorRuntimeCommand(handleAddActorRuntimeCommandRequest);
                }
                case DELETE_ACTOR -> {
                    final var deleteActorRuntimeCommandBody = (DeleteActorRuntimeCommandBodyModel) commandBody;
                    final var userId = deleteActorRuntimeCommandBody.getUserId();
                    final var playerId = deleteActorRuntimeCommandBody.getPlayerId();
                    final var clientId = deleteActorRuntimeCommandBody.getClientId();
                    final var handleDeleteActorRuntimeCommandRequest =
                            new HandleDeleteActorRuntimeCommandRequest(runtimeId, userId, playerId, clientId);
                    return contextService.handleDeleteActorRuntimeCommand(handleDeleteActorRuntimeCommandRequest);
                }
                case HANDLE_INCOMING -> {
                    final var handleIncomingRuntimeCommandBody = (HandleIncomingRuntimeCommandBodyModel) commandBody;
                    final var userId = handleIncomingRuntimeCommandBody.getUserId();
                    final var playerId = handleIncomingRuntimeCommandBody.getPlayerId();
                    final var clientId = handleIncomingRuntimeCommandBody.getClientId();
                    final var incoming = handleIncomingRuntimeCommandBody.getIncoming();
                    final var handleHandleIncomingRuntimeCommandRequest =
                            new HandleHandleIncomingRuntimeCommandRequest(runtimeId, userId, playerId, clientId,
                                    incoming);
                    return contextService.handleHandleIncomingRuntimeCommand(handleHandleIncomingRuntimeCommandRequest);
                }
                default -> {
                    runtimeCommand.setStatus(RuntimeCommandStatusEnum.FAILED);
                    return Uni.createFrom().voidItem();
                }
            }
        } else {
            runtimeCommand.setStatus(RuntimeCommandStatusEnum.FAILED);
            return Uni.createFrom().voidItem();
        }
    }

    private record UpdateRuntimeResult(List<RuntimeCommandModel> affectedCommands) {
    }
}
