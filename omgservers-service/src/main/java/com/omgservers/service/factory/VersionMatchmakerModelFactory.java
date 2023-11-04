package com.omgservers.service.factory;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionMatchmakerModelFactory {

    final GenerateIdOperation generateIdOperation;

    public VersionMatchmakerModel create(final Long tenantId,
                                         final Long versionId,
                                         final Long matchmakerId) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, versionId, matchmakerId);
    }

    public VersionMatchmakerModel create(final Long id,
                                         final Long tenantId,
                                         final Long versionId,
                                         final Long matchmakerId) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var model = new VersionMatchmakerModel();
        model.setId(id);
        model.setTenantId(tenantId);
        model.setVersionId(versionId);
        model.setCreated(now);
        model.setModified(now);
        model.setMatchmakerId(matchmakerId);
        model.setDeleted(false);
        return model;
    }
}
