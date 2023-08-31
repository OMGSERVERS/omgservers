package com.omgservers.module.matchmaker.factory;

import com.omgservers.model.matchmakerCommand.MatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandStatusEnum;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerCommandModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchmakerCommandModel create(final Long runtimeId,
                                         final MatchmakerCommandBodyModel body) {
        final var id = generateIdOperation.generateId();
        return create(id, runtimeId, body);
    }

    public MatchmakerCommandModel create(final Long id,
                                         final Long runtimeId,
                                         final MatchmakerCommandBodyModel body) {
        Instant now = Instant.now();

        final var matchmakerCommand = new MatchmakerCommandModel();
        matchmakerCommand.setId(id);
        matchmakerCommand.setRuntimeId(runtimeId);
        matchmakerCommand.setCreated(now);
        matchmakerCommand.setModified(now);
        matchmakerCommand.setQualifier(body.getQualifier());
        matchmakerCommand.setBody(body);
        matchmakerCommand.setStatus(MatchmakerCommandStatusEnum.NEW);
        return matchmakerCommand;
    }
}
