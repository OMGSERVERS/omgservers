package com.omgservers.module.internal.impl.service.eventShardedService.impl.method.fireEvent;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.internal.FireEventShardedRequest;
import com.omgservers.dto.internal.FireEventShardedResponse;
import com.omgservers.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FireEventMethodImpl implements FireEventMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertEventOperation upsertEventOperation;

    @Override
    public Uni<FireEventShardedResponse> fireEvent(final FireEventShardedRequest request) {
        FireEventShardedRequest.validate(request);

        final var event = request.getEvent();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertEventOperation.upsertEvent(changeContext, sqlConnection, event))
                .map(ChangeContext::getResult)
                .map(FireEventShardedResponse::new);
    }
}
