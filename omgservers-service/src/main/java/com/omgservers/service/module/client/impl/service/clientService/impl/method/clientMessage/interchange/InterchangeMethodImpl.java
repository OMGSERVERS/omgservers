package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMessage.interchange;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.message.MessageModel;
import com.omgservers.schema.model.message.body.ClientOutgoingMessageBodyDto;
import com.omgservers.schema.model.runtimeCommand.body.HandleMessageRuntimeCommandBodyDto;
import com.omgservers.schema.module.client.GetClientRequest;
import com.omgservers.schema.module.client.GetClientResponse;
import com.omgservers.schema.module.client.InterchangeRequest;
import com.omgservers.schema.module.client.InterchangeResponse;
import com.omgservers.schema.module.client.ViewClientRuntimeRefsRequest;
import com.omgservers.schema.module.client.ViewClientRuntimeRefsResponse;
import com.omgservers.schema.module.runtime.SyncClientCommandRequest;
import com.omgservers.schema.module.runtime.SyncClientCommandResponse;
import com.omgservers.schema.module.runtime.UpdateRuntimeAssignmentLastActivityRequest;
import com.omgservers.schema.module.runtime.UpdateRuntimeAssignmentLastActivityResponse;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.client.MessageModelFactory;
import com.omgservers.service.factory.runtime.RuntimeCommandModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.client.impl.operation.clientMessage.deleteClientMessagesByIds.DeleteClientMessagesByIdsOperation;
import com.omgservers.service.module.client.impl.operation.clientMessage.selectActiveClientMessagesByClientId.SelectActiveClientMessagesByClientIdOperation;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InterchangeMethodImpl implements InterchangeMethod {

    final RuntimeModule runtimeModule;
    final ClientModule clientModule;

    final SelectActiveClientMessagesByClientIdOperation selectActiveClientMessagesByClientIdOperation;
    final DeleteClientMessagesByIdsOperation deleteClientMessagesByIdsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;
    final MessageModelFactory messageModelFactory;

    @Override
    public Uni<InterchangeResponse> interchange(final InterchangeRequest request) {
        log.trace("Requested, {}", request);

        final var fromUserId = request.getFromUserId();
        final var clientId = request.getClientId();
        final var consumedMessages = request.getConsumedMessages();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> getClient(clientId)
                        .flatMap(client -> {
                            final var shard = shardModel.shard();

                            if (client.getUserId().equals(fromUserId)) {
                                if (client.getDeleted()) {
                                    // If client was deleted then only receiving is available
                                    return receiveMessages(shard, clientId, consumedMessages);
                                } else {
                                    return handleMessages(clientId, request.getOutgoingMessages())
                                            .flatMap(voidItem -> receiveMessages(shard, clientId, consumedMessages));
                                }
                            } else {
                                throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_CLIENT_ID,
                                        "wrong clientId, clientId=" + clientId);
                            }
                        })
                        .map(InterchangeResponse::new)
                );
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientModule.getService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<ClientRuntimeRefModel> selectClientRuntimeRef(final Long clientId) {
        return viewClientRuntimeRefs(clientId)
                .map(clientRuntimeRefs -> {
                    if (clientRuntimeRefs.isEmpty()) {
                        throw new ServerSideNotFoundException(ExceptionQualifierEnum.RUNTIME_NOT_FOUND,
                                String.format("runtime was not selected, clientId=%d", clientId));
                    } else {
                        return clientRuntimeRefs.stream()
                                .max(Comparator.comparing(ClientRuntimeRefModel::getId))
                                .get();
                    }
                });
    }

    Uni<List<ClientRuntimeRefModel>> viewClientRuntimeRefs(final Long clientId) {
        final var request = new ViewClientRuntimeRefsRequest(clientId);
        return clientModule.getService().viewClientRuntimeRefs(request)
                .map(ViewClientRuntimeRefsResponse::getClientRuntimeRefs);
    }

    Uni<Boolean> syncHandleMessageRuntimeCommand(final ClientRuntimeRefModel clientRuntimeRef,
                                                 final Object message) {
        final var clientId = clientRuntimeRef.getClientId();
        final var runtimeId = clientRuntimeRef.getRuntimeId();
        final var runtimeCommandBody = new HandleMessageRuntimeCommandBodyDto(clientId, message);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId,
                runtimeCommandBody);
        final var request = new SyncClientCommandRequest(clientId, runtimeCommand);
        return runtimeModule.getService().executeWithIdempotency(request)
                .map(SyncClientCommandResponse::getCreated);
    }

    Uni<Boolean> updateRuntimeAssignmentLastActivity(final Long runtimeId, final Long clientId) {
        final var request = new UpdateRuntimeAssignmentLastActivityRequest(runtimeId, clientId);
        return runtimeModule.getService().execute(request)
                .map(UpdateRuntimeAssignmentLastActivityResponse::getUpdated);
    }

    Uni<Void> handleMessages(final Long clientId, final List<MessageModel> messages) {
        // Runtime has to be assigned to client to handle client's outgoing messages
        return selectClientRuntimeRef(clientId)
                .onFailure(ServerSideNotFoundException.class).recoverWithNull()
                .onItem().ifNotNull().transformToUni(clientRuntimeRef -> {
                    final var runtimeId = clientRuntimeRef.getRuntimeId();
                    return updateRuntimeAssignmentLastActivity(runtimeId, clientId)
                            .flatMap(updated -> {
                                if (messages.isEmpty()) {
                                    return Uni.createFrom().voidItem();
                                } else{
                                    return Multi.createFrom().iterable(messages)
                                            .onItem().transformToUniAndConcatenate(message -> {
                                                if (message.getBody() instanceof final ClientOutgoingMessageBodyDto messageBody) {
                                                    return syncHandleMessageRuntimeCommand(clientRuntimeRef,
                                                            messageBody.getData());
                                                } else {
                                                    throw new ServerSideBadRequestException(
                                                            ExceptionQualifierEnum.WRONG_CLIENT_MESSAGE_BODY_TYPE,
                                                            "body type mismatch, " +
                                                                    message.getBody().getClass().getSimpleName());
                                                }
                                            })
                                            .collect().asList();
                                }
                            });
                })
                .replaceWithVoid();
    }

    Uni<List<MessageModel>> receiveMessages(final int shard,
                                            final Long clientId,
                                            final List<Long> consumedMessages) {
        return changeWithContextOperation.<List<MessageModel>>changeWithContext((changeContext, sqlConnection) ->
                        deleteClientMessagesByIdsOperation.deleteClientMessagesByIds(
                                        changeContext,
                                        sqlConnection,
                                        shard,
                                        clientId,
                                        consumedMessages)
                                .flatMap(deleted -> selectActiveClientMessagesByClientIdOperation
                                        .selectActiveClientMessagesByClientId(
                                                sqlConnection,
                                                shard,
                                                clientId)
                                        .map(clientMessages -> clientMessages.stream()
                                                .map(clientMessage ->
                                                        messageModelFactory.create(clientMessage.getId(),
                                                                clientMessage.getQualifier(),
                                                                clientMessage.getBody()))
                                                .toList()))
                )
                .map(ChangeContext::getResult);
    }
}
