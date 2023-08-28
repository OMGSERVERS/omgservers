package com.omgservers.factory;

import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchmakerModel create(final Long tenantId,
                                  final Long stageId) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, stageId);
    }

    public MatchmakerModel create(final Long id,
                                  final Long tenantId,
                                  final Long stageId) {
        Instant now = Instant.now();

        final var matchmaker = new MatchmakerModel();
        matchmaker.setId(id);
        matchmaker.setCreated(now);
        matchmaker.setModified(now);
        matchmaker.setTenantId(tenantId);
        matchmaker.setStageId(stageId);
        return matchmaker;
    }
}
