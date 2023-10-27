package com.omgservers.module.runtime.impl.service.doService.impl.method.doBroadcastMessage;

import com.omgservers.dto.internal.SyncEventRequest;
import com.omgservers.dto.internal.SyncEventResponse;
import com.omgservers.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.dto.runtime.DoBroadcastMessageResponse;
import com.omgservers.model.event.body.BroadcastApprovedEventBodyModel;
import com.omgservers.model.recipient.Recipient;
import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.module.runtime.impl.operation.selectRuntimeGrantsByRuntimeId.SelectRuntimeGrantsByRuntimeIdOperation;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.factory.EventModelFactory;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoBroadcastMessageMethodImpl implements DoBroadcastMessageMethod {

    final SystemModule systemModule;

    final SelectRuntimeGrantsByRuntimeIdOperation selectRuntimeGrantsByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final EventModelFactory eventModelFactory;

    final PgPool pgPool;

    @Override
    public Uni<DoBroadcastMessageResponse> doBroadcastMessage(final DoBroadcastMessageRequest request) {
        final var runtimeId = request.getRuntimeId();
        final var message = request.getMessage();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var grant = RuntimeGrantTypeEnum.MATCH_CLIENT;
                    return pgPool.withTransaction(sqlConnection -> selectRuntimeGrantsByRuntimeIdOperation
                            .selectRuntimeGrantsByRuntimeId(sqlConnection,
                                    shardModel.shard(),
                                    runtimeId)
                            .map(runtimeGrants -> createRecipientList(runtimeGrants, grant))
                            .flatMap(recipients -> syncApprove(runtimeId, recipients, message))
                    );
                })
                .replaceWith(new DoBroadcastMessageResponse(true));
    }

    List<Recipient> createRecipientList(final List<RuntimeGrantModel> runtimeGrants,
                                        final RuntimeGrantTypeEnum grantType) {
        return runtimeGrants.stream()
                .filter(runtimeGrant -> runtimeGrant.getType().equals(grantType))
                .map(runtimeGrant -> new Recipient(runtimeGrant.getShardKey(), runtimeGrant.getEntityId()))
                .toList();
    }

    Uni<Boolean> syncApprove(final Long runtimeId,
                             final List<Recipient> recipients,
                             final Object message) {
        final var eventBody = new BroadcastApprovedEventBodyModel(runtimeId, recipients, message);
        final var eventModel = eventModelFactory.create(eventBody);
        final var request = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEvent(request)
                .map(SyncEventResponse::getCreated);
    }
}
