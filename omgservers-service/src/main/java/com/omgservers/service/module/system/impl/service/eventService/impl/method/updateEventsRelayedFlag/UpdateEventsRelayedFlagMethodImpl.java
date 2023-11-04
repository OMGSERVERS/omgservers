package com.omgservers.service.module.system.impl.service.eventService.impl.method.updateEventsRelayedFlag;

import com.omgservers.model.dto.system.UpdateEventsRelayedFlagRequest;
import com.omgservers.model.dto.system.UpdateEventsRelayedFlagResponse;
import com.omgservers.service.module.system.impl.operation.updateEventsRelayedFlagByIds.UpdateEventsRelayedFlagByIdsOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateEventsRelayedFlagMethodImpl implements UpdateEventsRelayedFlagMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpdateEventsRelayedFlagByIdsOperation updateEventsRelayedFlagByIdsOperation;

    @Override
    public Uni<UpdateEventsRelayedFlagResponse> updateEventsRelayedFlag(UpdateEventsRelayedFlagRequest request) {
        final var ids = request.getIds();
        final var relayed = request.getRelayed();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> updateEventsRelayedFlagByIdsOperation
                                .updateEventsRelayedByIds(changeContext, sqlConnection, ids, relayed)
                )
                .map(ChangeContext::getResult)
                .map(UpdateEventsRelayedFlagResponse::new);
    }
}
