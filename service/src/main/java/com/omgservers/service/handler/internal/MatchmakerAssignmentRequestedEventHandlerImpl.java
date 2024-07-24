package com.omgservers.service.handler.internal;

import com.omgservers.model.dto.matchmaker.SyncMatchmakerAssignmentRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerAssignmentResponse;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRefsRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRefsResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.internal.MatchmakerAssignmentRequestedEventBodyModel;
import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.matchmaker.MatchmakerAssignmentModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerAssignmentRequestedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    final MatchmakerAssignmentModelFactory matchmakerAssignmentModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_ASSIGNMENT_REQUESTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerAssignmentRequestedEventBodyModel) event.getBody();
        final var clientId = body.getClientId();
        final var tenantId = body.getTenantId();
        final var versionId = body.getVersionId();

        return selectVersionMatchmakerRef(tenantId, versionId)
                .flatMap(versionMatchmakerRef -> {
                    final var matchmakerId = versionMatchmakerRef.getMatchmakerId();
                    final var idempotencyKey = event.getId().toString();

                    return syncMatchmakerAssignment(matchmakerId, clientId, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<VersionMatchmakerRefModel> selectVersionMatchmakerRef(final Long tenantId, final Long versionId) {
        return viewVersionMatchmakerRefs(tenantId, versionId)
                .map(refs -> {
                    if (refs.isEmpty()) {
                        throw new ServerSideNotFoundException(
                                ExceptionQualifierEnum.MATCHMAKER_NOT_FOUND,
                                String.format("matchmaker was not selected, version=%d/%d", tenantId, versionId));
                    } else {
                        final var randomRefIndex = ThreadLocalRandom.current().nextInt(refs.size()) % refs.size();
                        final var randomMatchmakerRef = refs.get(randomRefIndex);
                        return randomMatchmakerRef;
                    }
                });
    }

    Uni<List<VersionMatchmakerRefModel>> viewVersionMatchmakerRefs(final Long tenantId, final Long versionId) {
        final var request = new ViewVersionMatchmakerRefsRequest(tenantId, versionId);
        return tenantModule.getVersionService().viewVersionMatchmakerRefs(request)
                .map(ViewVersionMatchmakerRefsResponse::getVersionMatchmakerRefs);
    }

    Uni<Boolean> syncMatchmakerAssignment(final Long matchmakerId,
                                          final Long clientId,
                                          final String idempotencyKey) {
        final var matchmakerAssignment = matchmakerAssignmentModelFactory.create(matchmakerId,
                clientId,
                idempotencyKey);
        final var request = new SyncMatchmakerAssignmentRequest(matchmakerAssignment);
        return matchmakerModule.getMatchmakerService().syncMatchmakerAssignment(request)
                .map(SyncMatchmakerAssignmentResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", matchmakerAssignment, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
