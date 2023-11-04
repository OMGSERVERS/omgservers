package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doMulticastMessage;

import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.model.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.model.event.body.MulticastApprovedEventBodyModel;
import com.omgservers.model.recipient.Recipient;
import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.service.module.runtime.impl.operation.selectRuntimeGrantsByRuntimeIdAndEntityIds.SelectRuntimeGrantsByRuntimeIdAndEntityIdsOperation;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoMulticastMessageMethodImpl implements DoMulticastMessageMethod {

    final SystemModule systemModule;

    final SelectRuntimeGrantsByRuntimeIdAndEntityIdsOperation selectRuntimeGrantsByRuntimeIdAndEntityIdsOperation;
    final CheckShardOperation checkShardOperation;

    final EventModelFactory eventModelFactory;

    final PgPool pgPool;

    @Override
    public Uni<DoMulticastMessageResponse> doMulticastMessage(final DoMulticastMessageRequest request) {
        final var runtimeId = request.getRuntimeId();
        final var recipients = request.getRecipients();
        final var message = request.getMessage();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var grant = RuntimeGrantTypeEnum.MATCH_CLIENT;
                    final var clientIds = recipients.stream().map(Recipient::clientId).toList();

                    return pgPool.withTransaction(sqlConnection -> selectRuntimeGrantsByRuntimeIdAndEntityIdsOperation
                            .selectRuntimeGrantsByRuntimeIdAndEntityIds(
                                    sqlConnection,
                                    shardModel.shard(),
                                    runtimeId,
                                    clientIds)
                            .flatMap(runtimeGrants -> {
                                if (checkGrants(recipients, runtimeGrants, grant)) {
                                    return syncApprove(runtimeId, recipients, message);
                                } else {
                                    throw new ServerSideForbiddenException(
                                            String.format("grants were not found, " +
                                                            "runtimeId=%s, recipients=%s, grant=%s",
                                                    runtimeId, recipients, grant));
                                }
                            })
                    );
                })
                .replaceWith(new DoMulticastMessageResponse(true));
    }

    boolean checkGrants(final List<Recipient> recipients,
                        final List<RuntimeGrantModel> runtimeGrants,
                        final RuntimeGrantTypeEnum grant) {
        final var grantSet = runtimeGrants.stream()
                .filter(runtimeGrant -> runtimeGrant.getType().equals(grant))
                .map(RuntimeGrantModel::getEntityId)
                .collect(Collectors.toSet());

        return recipients.stream()
                .allMatch(recipient -> grantSet.contains(recipient.clientId()));
    }

    Uni<Boolean> syncApprove(final Long runtimeId,
                             final List<Recipient> recipients,
                             final Object message) {
        final var eventBody = new MulticastApprovedEventBodyModel(runtimeId, recipients, message);
        final var eventModel = eventModelFactory.create(eventBody);
        final var request = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEvent(request)
                .map(SyncEventResponse::getCreated);
    }
}
