package com.omgservers.service.operation.runtime;

import com.omgservers.schema.message.body.RuntimeCreatedMessageBodyDto;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.shard.runtime.runtimeMessage.SyncRuntimeMessageRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.SyncRuntimeMessageResponse;
import com.omgservers.service.factory.runtime.RuntimeMessageModelFactory;
import com.omgservers.service.shard.runtime.RuntimeShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateRuntimeCreatedRuntimeMessageOperationImpl implements CreateRuntimeCreatedRuntimeMessageOperation {

    final RuntimeShard runtimeShard;

    final RuntimeMessageModelFactory runtimeMessageModelFactory;

    @Override
    public Uni<Boolean> execute(final RuntimeModel runtime,
                                final String idempotencyKey) {
        final var commandBody = new RuntimeCreatedMessageBodyDto(runtime.getConfig());
        final var runtimeMessage = runtimeMessageModelFactory.create(runtime.getId(),
                commandBody,
                idempotencyKey);

        final var request = new SyncRuntimeMessageRequest(runtimeMessage);
        return runtimeShard.getService().executeWithIdempotency(request)
                .map(SyncRuntimeMessageResponse::getCreated);
    }
}
