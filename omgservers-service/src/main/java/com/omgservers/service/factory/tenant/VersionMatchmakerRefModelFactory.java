package com.omgservers.service.factory.tenant;

import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionMatchmakerRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public VersionMatchmakerRefModel create(final Long tenantId,
                                            final Long versionId,
                                            final Long matchmakerId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, versionId, matchmakerId, idempotencyKey);
    }

    public VersionMatchmakerRefModel create(final Long tenantId,
                                            final Long versionId,
                                            final Long matchmakerId,
                                            final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, versionId, matchmakerId, idempotencyKey);
    }

    public VersionMatchmakerRefModel create(final Long id,
                                            final Long tenantId,
                                            final Long versionId,
                                            final Long matchmakerId,
                                            final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var versionMatchmakerRef = new VersionMatchmakerRefModel();
        versionMatchmakerRef.setId(id);
        versionMatchmakerRef.setTenantId(tenantId);
        versionMatchmakerRef.setVersionId(versionId);
        versionMatchmakerRef.setCreated(now);
        versionMatchmakerRef.setModified(now);
        versionMatchmakerRef.setIdempotencyKey(idempotencyKey);
        versionMatchmakerRef.setMatchmakerId(matchmakerId);
        versionMatchmakerRef.setDeleted(false);
        return versionMatchmakerRef;
    }
}
