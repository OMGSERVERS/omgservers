package com.omgservers.service.factory;

import com.omgservers.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionMatchmakerRequestModelFactory {

    final GenerateIdOperation generateIdOperation;

    public VersionMatchmakerRequestModel create(final Long tenantId,
                                                final Long versionId) {
        final var id = generateIdOperation.generateId();
        final var matchmakerId = generateIdOperation.generateId();
        return create(id, tenantId, versionId, matchmakerId);
    }

    public VersionMatchmakerRequestModel create(final Long id,
                                                final Long tenantId,
                                                final Long versionId,
                                                final Long matchmakerId) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var versionMatchmakerRequest = new VersionMatchmakerRequestModel();
        versionMatchmakerRequest.setId(id);
        versionMatchmakerRequest.setTenantId(tenantId);
        versionMatchmakerRequest.setVersionId(versionId);
        versionMatchmakerRequest.setCreated(now);
        versionMatchmakerRequest.setModified(now);
        versionMatchmakerRequest.setMatchmakerId(matchmakerId);
        versionMatchmakerRequest.setDeleted(false);
        return versionMatchmakerRequest;
    }
}