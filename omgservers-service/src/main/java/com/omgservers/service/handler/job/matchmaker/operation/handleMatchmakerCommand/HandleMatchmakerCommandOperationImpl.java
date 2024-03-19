package com.omgservers.service.handler.job.matchmaker.operation.handleMatchmakerCommand;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.model.matchmakerState.MatchmakerStateModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class HandleMatchmakerCommandOperationImpl implements HandleMatchmakerCommandOperation {

    final Map<MatchmakerCommandQualifierEnum, MatchmakerCommandHandler> matchmakerCommandHandlers;

    HandleMatchmakerCommandOperationImpl(final Instance<MatchmakerCommandHandler> matchmakerCommandHandlerBeans) {
        this.matchmakerCommandHandlers = new ConcurrentHashMap<>();
        matchmakerCommandHandlerBeans.stream().forEach(matchmakerCommandHandler -> {
            final var qualifier = matchmakerCommandHandler.getQualifier();
            if (matchmakerCommandHandlers.put(qualifier, matchmakerCommandHandler) != null) {
                log.error("Multiple matchmaker command handlers were detected, qualifier={}", qualifier);
            }
        });
    }

    @Override
    public Uni<Void> handleMatchmakerCommand(final MatchmakerStateModel matchmakerStateModel,
                                             final MatchmakerChangeOfStateModel changeOfState,
                                             final MatchmakerCommandModel matchmakerCommand) {
        final var qualifier = matchmakerCommand.getQualifier();
        final var qualifierBodyClass = qualifier.getBodyClass();
        final var body = matchmakerCommand.getBody();

        if (!qualifierBodyClass.isInstance(body)) {
            log.error("Qualifier and matchmaker command body do not match, qualifier={}, bodyClass={}, id={}",
                    matchmakerCommand.getQualifier(), body.getClass(), matchmakerCommand.getId());
            return Uni.createFrom().voidItem();
        }

        if (!matchmakerCommandHandlers.containsKey(qualifier)) {
            log.error("Matchmaker command handler was not found, qualifier={}, id={}",
                    matchmakerCommand.getQualifier(), matchmakerCommand.getId());
            return Uni.createFrom().voidItem();
        }

        return matchmakerCommandHandlers.get(qualifier)
                .handle(matchmakerStateModel, changeOfState, matchmakerCommand);
    }
}
