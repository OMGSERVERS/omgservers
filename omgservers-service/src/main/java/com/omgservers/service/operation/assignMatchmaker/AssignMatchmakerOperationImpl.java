package com.omgservers.service.operation.assignMatchmaker;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerAssignmentResponse;
import com.omgservers.service.factory.matchmaker.MatchmakerAssignmentModelFactory;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AssignMatchmakerOperationImpl implements AssignMatchmakerOperation {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    final MatchmakerAssignmentModelFactory matchmakerAssignmentModelFactory;

    @Override
    public Uni<Boolean> execute(final Long clientId,
                                final Long matchmakerId,
                                final String idempotencyKey) {
        return createMatchmakerAssignment(matchmakerId, clientId, idempotencyKey);
    }

    Uni<Boolean> createMatchmakerAssignment(final Long matchmakerId,
                                            final Long clientId,
                                            final String idempotencyKey) {
        final var matchmakerAssignment = matchmakerAssignmentModelFactory
                .create(matchmakerId, clientId, idempotencyKey);
        final var request = new SyncMatchmakerAssignmentRequest(matchmakerAssignment);
        return matchmakerModule.getService().executeWithIdempotency(request)
                .map(SyncMatchmakerAssignmentResponse::getCreated);
    }
}
