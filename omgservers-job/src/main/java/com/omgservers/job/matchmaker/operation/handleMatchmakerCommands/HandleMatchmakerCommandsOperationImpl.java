package com.omgservers.job.matchmaker.operation.handleMatchmakerCommands;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.model.matchmakerState.IndexedMatchmakerState;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class HandleMatchmakerCommandsOperationImpl implements HandleMatchmakerCommandsOperation {

    final Map<MatchmakerCommandQualifierEnum, MatchmakerCommandHandler> matchmakerCommandHandlers;

    HandleMatchmakerCommandsOperationImpl(final Instance<MatchmakerCommandHandler> matchmakerCommandHandlerBeans) {
        this.matchmakerCommandHandlers = new ConcurrentHashMap<>();
        matchmakerCommandHandlerBeans.stream().forEach(matchmakerCommandHandler -> {
            final var qualifier = matchmakerCommandHandler.getQualifier();
            if (matchmakerCommandHandlers.put(qualifier, matchmakerCommandHandler) != null) {
                log.error("Multiple matchmaker command handlers were detected, qualifier={}", qualifier);
            } else {
                log.debug("Matchmaker command handler was added, qualifier={}, handler={}",
                        qualifier, matchmakerCommandHandler.getClass().getSimpleName());
            }
        });
    }

    @Override
    public Uni<Void> handleMatchmakerCommands(final Long matchmakerId,
                                              final IndexedMatchmakerState indexedMatchmakerState,
                                              final MatchmakerChangeOfState matchmakerChangeOfState) {
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var matchmakerCommands = indexedMatchmakerState.getMatchmakerState().getMatchmakerCommands();
                    if (matchmakerCommands.isEmpty()) {
                        log.debug("There aren't any matchmaker commands, matchmakerId={}", matchmakerId);
                    } else {
                        matchmakerCommands.forEach(matchmakerCommand -> handleMatchmakerCommand(
                                indexedMatchmakerState,
                                matchmakerChangeOfState,
                                matchmakerCommand)
                        );
                    }
                });
    }

    void handleMatchmakerCommand(final IndexedMatchmakerState indexedMatchmakerState,
                                 final MatchmakerChangeOfState matchmakerChangeOfState,
                                 final MatchmakerCommandModel matchmakerCommand) {
        final var qualifier = matchmakerCommand.getQualifier();
        final var qualifierBodyClass = qualifier.getBodyClass();
        final var body = matchmakerCommand.getBody();

        if (!qualifierBodyClass.isInstance(body)) {
            log.error("Qualifier and matchmaker command body do not match, id={}, qualifier={}, bodyClass={}",
                    matchmakerCommand.getId(), matchmakerCommand.getQualifier(), body.getClass());
            return;
        }

        if (!matchmakerCommandHandlers.containsKey(qualifier)) {
            log.error("Matchmaker command handler was not found, id={}, qualifier={}",
                    matchmakerCommand.getId(), matchmakerCommand.getQualifier());
            return;
        }

        matchmakerCommandHandlers.get(qualifier)
                .handle(indexedMatchmakerState, matchmakerChangeOfState, matchmakerCommand);
    }
}
